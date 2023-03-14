package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CommentView;
import actions.views.PostView;
import actions.views.TopicView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.CommentService;
import services.TopicService;

/**
 * トピックに関する処理を行うActionクラス
 *
 */
public class TopicAction extends ActionBase {

    private TopicService service;
    private CommentService comservice;
    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new TopicService();
        comservice = new CommentService();

        //メソッドを実行
        invoke();
        service.close();
        comservice.close();
    }

    /**
     * コメントデータを取得
     * @throws ServletException
     * @throws IOException
     */
    private void setCommentViewListTo(int page) {
        TopicView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TOP_ID)));  //トピック情報を取得
        List<CommentView> comments = comservice.getMinePerPage(tv, page); //指定されたページ数の一覧画面に表示するコメントデータを取得
        long commentsCount = comservice.countAllMine(tv);  // 全コメントデータの件数を取得
        putRequestScope(AttributeConst.COMMENTS, comments); // 取得したコメントデータ
        putRequestScope(AttributeConst.COM_COUNT, commentsCount); // 全てのコメントデータの件数
        putRequestScope(AttributeConst.PAGE, page); // ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_COMMENT); // 1ページに表示するレコードの数
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());
        putRequestScope(AttributeConst.TOPIC, new TopicView()); //空のトピックインスタンス

        //指定されたページ数の一覧画面に表示するトピックデータを取得
        int page = getPage();
        List<TopicView> topics = service.getAllPerPage(page);

        //全トピックデータの件数を取得
        long topicsCount = service.countAll();
        putRequestScope(AttributeConst.TOPICS, topics); //取得したトピックデータ
        putRequestScope(AttributeConst.TOP_COUNT, topicsCount); //全てのトピックデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_TOPIC); //1ページに表示するレコードの数

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
     * トピック内容を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //idを条件にトピックデータを取得する
        TopicView rv = service.findOne(toNumber(getRequestParam(AttributeConst.TOP_ID)));

        if (rv == null) {
            //該当のトピックデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOPIC, rv); //取得したトピックデータ

            //指定されたページ数の一覧画面に表示するコメントデータを取得
            int page = getPage();
            setCommentViewListTo(page);

            //トピック内容を表示
            forward(ForwardConst.FW_TOPI_SHOW);
        }
    }

    /**
     * コメントの作成を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //セッションからログイン中の利用者情報を取得
            PostView ev = (PostView) getSessionScope(AttributeConst.LOGIN_POS);

            //トピック情報を取得
            TopicView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TOP_ID)));

            //パラメータの値をもとにコメント情報のインスタンスを作成する
            CommentView rv = new CommentView(
                    null,
                    ev, //ログインしている利用者を、コメント作成者として登録する
                    tv,//投稿しているトピック情報を登録する
                    getRequestParam(AttributeConst.COM_TITLE),
                    null);

            putRequestScope(AttributeConst.TOPIC, rv); //トピック情報を持ったコメントインスタンスをセット

            //コメント情報登録
            List<String> errors = comservice.create(rv);

            //トピックのid番号を取得
            int tid = toNumber(getRequestParam(AttributeConst.TOP_ID));

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TOPIC, rv);//入力されたコメント情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //指定されたページ数の一覧画面に表示するコメントデータを取得
                int page = getPage();
                setCommentViewListTo(page);

                //登録失敗エラーメッセージ表示フラグをたてる
                putRequestScope(AttributeConst.COM_ERR, true);

                // トピック詳細画面に遷移
                forward(ForwardConst.FW_TOPI_SHOW, tid);

            } else {
                //登録中にエラーがなかった場合

                //指定されたページ数の一覧画面に表示するコメントデータを取得
                int page = getPage();
                setCommentViewListTo(page);

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_NEW_COMMENT.getMessage());

                // トピック詳細画面に遷移

                redirect(ForwardConst.ACT_TOPI, ForwardConst.CMD_SHOW, tid);
            }
        }
    }
}