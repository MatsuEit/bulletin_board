package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.TopicView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.TopicService;

/**
 * トピックに関する処理を行うActionクラス
 *
 */
public class TopicAction extends ActionBase {

    private TopicService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new TopicService();

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
        forward(ForwardConst.FW_REP_INDEX);
    }

}