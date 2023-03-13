package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.PostView;
import actions.views.TopicView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.TopicService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private TopicService service;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new TopicService();
        //メソッドを実行
        invoke();
        service.close();

    }

    /**
     * トピックデータを取得
     * @param page ページ数
     */
    private void setContents(int page) {
        List<TopicView> topics = service.getAllPerPage(page); //指定されたページ数の一覧画面に表示する日報データを取得
        long topicsCount = service.countAll();        //全日報データの件数を取得
        putRequestScope(AttributeConst.TOPICS, topics); //取得した日報データ
        putRequestScope(AttributeConst.TOP_COUNT, topicsCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_TOPIC); //1ページに表示するレコードの数
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {
        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());
        putRequestScope(AttributeConst.TOPIC, new TopicView()); //空のトピックインスタンス

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        setContents(page);

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

    /**
     * トピックの作成を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.TOPIC, new TopicView()); //空のトピックインスタンス

            //セッションからログイン中の利用者情報を取得
            PostView ev = (PostView) getSessionScope(AttributeConst.LOGIN_POS);

            //パラメータの値をもとにトピック情報のインスタンスを作成する
            TopicView rv = new TopicView(
                    null,
                    ev, //ログインしている利用者を、トピック作成者として登録する
                    getRequestParam(AttributeConst.TOP_TITLE),
                    null);

            //トピック情報登録
            List<String> errors = service.create(rv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TOPIC, rv);//入力されたトピック情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //指定されたページ数の一覧画面に表示する日報データを取得
                int page = getPage();
                setContents(page);

                //登録失敗エラーメッセージ表示フラグをたてる
                putRequestScope(AttributeConst.TOP_ERR, true);

                //一覧画面を再表示
                forward(ForwardConst.FW_TOP_INDEX);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //指定されたページ数の一覧画面に表示する日報データを取得
                int page = getPage();
                setContents(page);

                //一覧画面を再表示
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }


        }

    }
}