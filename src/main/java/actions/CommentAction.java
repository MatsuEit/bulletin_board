package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CommentView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.CommentService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class CommentAction extends ActionBase {

    private CommentService service;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new CommentService();
        //メソッドを実行
        invoke();
        service.close();

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {
        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());
        putRequestScope(AttributeConst.COMMENT, new CommentView()); //空のコメントインスタンス

        //指定されたページ数の一覧画面に表示するコメントデータを取得
        int page = getPage();
        List<CommentView> topics = service.getAllPerPage(page);

        //全コメントデータの件数を取得
        long topicsCount = service.countAll();
        putRequestScope(AttributeConst.COMMENTS, topics); //取得したコメントデータ
        putRequestScope(AttributeConst.COM_COUNT, topicsCount); //全てのコメントデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_COMMENT); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOPI_SHOW);
    }

//    /**
//     * コメントの作成を行う
//     * @throws ServletException
//     * @throws IOException
//     */
//    public void create() throws ServletException, IOException {
//
//        //CSRF対策 tokenのチェック
//        if (checkToken()) {
//            //CSRF対策用トークンを設定
//            putRequestScope(AttributeConst.TOKEN, getTokenId());
//            putRequestScope(AttributeConst.COMMENT, new CommentView()); //空のコメントインスタンス
//
//            //セッションからログイン中の利用者情報を取得
//            PostView ev = (PostView) getSessionScope(AttributeConst.LOGIN_POS);
//
//            //トピック情報を取得
//
//            //パラメータの値をもとにコメント情報のインスタンスを作成する
//            CommentView rv = new CommentView(
//                    null,
//                    ev, //ログインしている利用者を、コメント作成者として登録する
//
//                    getRequestParam(AttributeConst.COM_TITLE),
//                    null);
//
//            //コメント情報登録
//            List<String> errors = service.create(rv);
//
//            if (errors.size() > 0) {
//                //登録中にエラーがあった場合
//
//                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
//                putRequestScope(AttributeConst.COMMENT, rv);//入力されたコメント情報
//                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト
//
//                //指定されたページ数の一覧画面に表示するコメントデータを取得
//                int page = getPage();
//                List<CommentView> topics = service.getAllPerPage(page);
//
//                //全コメントデータの件数を取得
//                long topicsCount = service.countAll();
//                putRequestScope(AttributeConst.COMMENTS, topics); //取得したコメントデータ
//                putRequestScope(AttributeConst.COM_COUNT, topicsCount); //全てのコメントデータの件数
//                putRequestScope(AttributeConst.PAGE, page); //ページ数
//                putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_COMMENT); //1ページに表示するレコードの数
//
//                //登録失敗エラーメッセージ表示フラグをたてる
//                putRequestScope(AttributeConst.COM_ERR, true);
//
//                //一覧画面を再表示
//                forward(ForwardConst.FW_COM_INDEX);
//
//            } else {
//                //登録中にエラーがなかった場合
//
//                //セッションに登録完了のフラッシュメッセージを設定
//                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());
//
//                //指定されたページ数の一覧画面に表示するコメントデータを取得
//                int page = getPage();
//                List<CommentView> topics = service.getAllPerPage(page);
//
//                //全コメントデータの件数を取得
//                long topicsCount = service.countAll();
//                putRequestScope(AttributeConst.COMMENTS, topics); //取得したコメントデータ
//                putRequestScope(AttributeConst.COM_COUNT, topicsCount); //全てのコメントデータの件数
//                putRequestScope(AttributeConst.PAGE, page); //ページ数
//                putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE_COMMENT); //1ページに表示するレコードの数
//
//                //一覧画面を再表示
//                forward(ForwardConst.FW_COM_INDEX);
//            }


        }