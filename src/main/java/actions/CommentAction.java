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


        }