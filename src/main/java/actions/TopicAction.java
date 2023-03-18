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
     * トピック内容を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //idを条件にトピックデータを取得する
        TopicView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TOP_ID)));
        putRequestScope(AttributeConst.TOPIC, tv); //トピック情報を持ったコメントインスタンスをセット

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        if (tv == null) {
            //該当のトピックデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            //指定されたページ数の一覧画面に表示するコメントデータを取得
            setCommentViewListTo(getPage());

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
            CommentView cv = new CommentView(
                    null,
                    ev, //ログインしている利用者を、コメント作成者として登録する
                    tv,//投稿しているトピック情報を登録する
                    getRequestParam(AttributeConst.COM_TITLE),
                    null);

            putRequestScope(AttributeConst.TOPIC, cv); //トピック情報を持ったコメントインスタンスをセット
            //コメント情報登録
            List<String> errors = comservice.create(cv);

            //トピックのid番号を取得
            int tid = toNumber(getRequestParam(AttributeConst.TOP_ID));
            putRequestScope(AttributeConst.TOP_ID ,tid);

            //指定されたページ数の一覧画面に表示するコメントデータを取得
            setCommentViewListTo(getPage());

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.ERR, errors); //エラーメッセージを設定
                putRequestScope(AttributeConst.TOPIC, tv); //トピック情報をセット

                //登録失敗エラーメッセージ表示フラグをたてる
                putRequestScope(AttributeConst.COM_ERR, true);

                //トピック詳細画面に遷移
                forward(ForwardConst.FW_TOPI_SHOW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_NEW_COMMENT.getMessage());

                //トピック詳細画面に遷移
                redirect(ForwardConst.ACT_TOPI, ForwardConst.CMD_SHOW, tid);
            }
        }
    }
}