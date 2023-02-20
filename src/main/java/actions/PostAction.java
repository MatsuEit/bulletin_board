package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.PostView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.PostService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class PostAction extends ActionBase {

    private PostService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new PostService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<PostView> posts = service.getPerPage(page);


        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_POS_INDEX);

    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.POST, new PostView()); //空の従業員インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_POS_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元に従業員情報のインスタンスを作成する
            PostView ev = new PostView(
                    null,
                    getRequestParam(AttributeConst.POS_NAME),
                    getRequestParam(AttributeConst.POS_PASS),
                    null);

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報登録
            List<String> errors = service.create(ev, pepper);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.POST, ev); //入力された利用者情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_POS_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_POS, ForwardConst.CMD_INDEX);
            }

        }
    }

}