package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.PostView;
import actions.views.TopicView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.PostService;

/**
 * 認証に関する処理を行うActionクラス
 *
 */
public class AuthAction extends ActionBase {

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
     * ログイン画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //ログイン画面を表示
        forward(ForwardConst.FW_LOGIN);
    }

    /**
     * ログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {

        String name = getRequestParam(AttributeConst.POS_NAME);
        String plainPass = getRequestParam(AttributeConst.POS_PASS);
        String pepper = getContextScope(PropertyConst.PEPPER);


        //有効な利用者か認証する
        Boolean isValidPost = service.validateLogin(name, plainPass, pepper);

        if (isValidPost) {
            //認証成功の場合

            //CSRF対策 tokenのチェック
            if (checkToken()) {
                //CSRF対策用トークンを設定
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.TOPIC, new TopicView()); //空のトピックインスタンス
//                System.out.println("----------------------------------------");
//                System.out.println("token=" + ${_token});
//                System.out.println("----------------------------------------");

                //ログインした利用者のDBデータを取得
                PostView ev = service.findOne(name, plainPass, pepper);
                //セッションにログインした利用者を設定
                putSessionScope(AttributeConst.LOGIN_POS, ev);
                //セッションにログイン完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGINED.getMessage());
                //トップページへリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        } else {
            //認証失敗の場合

            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //認証失敗エラーメッセージ表示フラグをたてる
            putRequestScope(AttributeConst.LOGIN_ERR, true);
            //入力された利用名を設定
            putRequestScope(AttributeConst.POS_NAME, name);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);
        }
    }

    /**
     * ログアウト処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException, IOException {

        //セッションからログイン利用者のパラメータを削除
        removeSessionScope(AttributeConst.LOGIN_POS);

        //セッションにログアウト時のフラッシュメッセージを追加
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGOUT.getMessage());

        //ログイン画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);

    }

}