package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.PostConverter;
import actions.views.PostView;
import actions.views.TopicConverter;
import actions.views.TopicView;
import constants.JpaConst;
import models.Post;
import models.Topic;
import models.validators.PostValidator;
import utils.EncryptUtil;

/**
 * 利用者テーブルの操作に関わる処理を行うクラス
 */
public class PostService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、PostViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<PostView> getPerPage(int page) {
        List<Post> posts = em.createNamedQuery(JpaConst.Q_POS_GET_ALL, Post.class)
                .getResultList();

        return PostConverter.toViewList(posts);
    }

    /**
     * 名前、パスワードを条件に取得したデータをPostViewのインスタンスで返却する
     * @param name 名前
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public PostView findOne(String name, String plainPass, String pepper) {
        Post e = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //名前とハッシュ化済パスワードを条件に利用者を1件取得する
            e = em.createNamedQuery(JpaConst.Q_POS_GET_BY_NAME_AND_PASS, Post.class)
                    .setParameter(JpaConst.JPQL_PARM_NAME, name)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return PostConverter.toView(e);

    }

    /**
     * idを条件に取得したデータをPostViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public PostView findOne(int id) {
        Post e = findOneInternal(id);
        return PostConverter.toView(e);
    }

    /**
     * 名前を条件に該当するデータの件数を取得し、返却する
     * @param name 名前
     * @return 該当するデータの件数
     */
    public long countByName(String name) {

        //指定した利用者の件数を取得する
        long posts_count = (long) em.createNamedQuery(JpaConst.Q_POS_COUNT_REGISTERED_BY_NAME, Long.class)
                .setParameter(JpaConst.JPQL_PARM_NAME, name)
                .getSingleResult();
        return posts_count;
    }

    /**
     * 画面から入力された利用者の登録内容を元にデータを1件作成し、利用者テーブルに登録する
     * @param ev 画面から入力された利用者の登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(PostView ev, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper);
        ev.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        ev.setCreatedAt(now);

        //登録内容のバリデーションを行う
        List<String> errors = PostValidator.validate(this, ev, true, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(ev);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 名前とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param name 名前
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String name, String plainPass, String pepper) {

        boolean isValidPost = false;
        if (name != null && !name.equals("") && plainPass != null && !plainPass.equals("")) {
            PostView ev = findOne(name, plainPass, pepper);

            if (ev != null && ev.getId() != null) {

                //データが取得できた場合、認証成功
                isValidPost = true;
            }
        }

        //認証結果を返却する
        return isValidPost;
    }

    /**
     * idを条件にデータを1件取得し、Postのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Post findOneInternal(int id) {
        Post e = em.find(Post.class, id);

        return e;
    }

    /**
     * 利用者データを1件登録する
     * @param ev 利用者データ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(PostView ev) {

        em.getTransaction().begin();
        em.persist(PostConverter.toModel(ev));
        em.getTransaction().commit();
    }

    /**
     * 指定されたページ数の一覧画面に表示するトピックデータを取得し、TopicViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<TopicView> getAllPerPage(int page) {

        List<Topic> topics = em.createNamedQuery(JpaConst.Q_TOP_GET_ALL, Topic.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE_TOPIC * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE_TOPIC)
                .getResultList();
        return TopicConverter.toViewList(topics);
    }

    /**
     * トピックテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long topics_count = (long) em.createNamedQuery(JpaConst.Q_TOP_COUNT, Long.class)
                .getSingleResult();
        return topics_count;
    }
}