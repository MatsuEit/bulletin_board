package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.CommentConverter;
import actions.views.CommentView;
import constants.JpaConst;
import models.Comment;
import models.validators.CommentValidator;

/**
 * コメントテーブルの操作に関わる処理を行うクラス
 */
public class CommentService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するコメントデータを取得し、CommentViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<CommentView> getAllPerPage(int page) {

        List<Comment> Comments = em.createNamedQuery(JpaConst.Q_TOP_GET_ALL, Comment.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE_COMMENT * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE_COMMENT)
                .getResultList();
        return CommentConverter.toViewList(Comments);
    }

    /**
     * コメントテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long Comments_count = (long) em.createNamedQuery(JpaConst.Q_TOP_COUNT, Long.class)
                .getSingleResult();
        return Comments_count;
    }

    /**
     * idを条件に取得したデータをCommentViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CommentView findOne(int id) {
        return CommentConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力されたコメントの登録内容を元にデータを1件作成し、コメントテーブルに登録する
     * @param rv コメントの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(CommentView rv) {
        List<String> errors = CommentValidator.validate(rv);
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
    private Comment findOneInternal(int id) {
        return em.find(Comment.class, id);
    }

    /**
     * コメントデータを1件登録する
     * @param rv コメントデータ
     */
    private void createInternal(CommentView rv) {
        em.getTransaction().begin();
        Comment t = CommentConverter.toModel(rv);
        em.persist(t);
        em.getTransaction().commit();
    }

}