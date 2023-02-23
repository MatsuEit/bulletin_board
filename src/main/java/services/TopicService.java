package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.TopicConverter;
import actions.views.TopicView;
import constants.JpaConst;
import models.Topic;
import models.validators.TopicValidator;

/**
 * トピックテーブルの操作に関わる処理を行うクラス
 */
public class TopicService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するトピックデータを取得し、TopicViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<TopicView> getAllPerPage(int page) {

        List<Topic> reports = em.createNamedQuery(JpaConst.Q_TOP_GET_ALL, Topic.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE_TOPIC * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE_TOPIC)
                .getResultList();
        return TopicConverter.toViewList(reports);
    }

    /**
     * トピックテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_TOP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * idを条件に取得したデータをTopicViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TopicView findOne(int id) {
        return TopicConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力されたトピックの登録内容を元にデータを1件作成し、トピックテーブルに登録する
     * @param rv トピックの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(TopicView rv) {
        List<String> errors = TopicValidator.validate(rv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            createInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Topic findOneInternal(int id) {
        return em.find(Topic.class, id);
    }

    /**
     * トピックデータを1件登録する
     * @param rv トピックデータ
     */
    private void createInternal(TopicView rv) {
        em.getTransaction().begin();
        em.persist(TopicConverter.toModel(rv));
        em.getTransaction().commit();
    }

}