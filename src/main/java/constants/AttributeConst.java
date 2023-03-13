package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の利用者
    LOGIN_POS("login_post"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //利用者管理
    POST("post"),
    POSTS("posts"),
    POS_COUNT("posts_count"),
    POS_ID("id"),
    POS_PASS("password"),
    POS_NAME("name"),

    //トピック管理
    TOPIC("topic"),
    TOPICS("topics"),
    TOP_COUNT("topics_count"),
    TOP_ID("id"),
    TOP_TITLE("title"),
    TOP_ERR("topicError"),
    TOP_IN("topic_in"),

    //コメント管理
    COMMENT("comment"),
    COMMENTS("comments"),
    COM_COUNT("comments_count"),
    COM_ID("id"),
    COM_DATETIME("comment_datetime"),
    COM_TITLE("title"),
    COM_ERR("commentError"),
    COM_like("like");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}