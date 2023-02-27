package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "bulletin_board";

    //トピックデータ取得件数の最大値
    int ROW_PER_PAGE_TOPIC = 15; //1ページに表示するレコードの数
    //コメントデータ取得件数の最大値
    int ROW_PER_PAGE_COMMENT = 50; //1ページに表示するレコードの数

    //利用者テーブル
    String TABLE_POS = "posts"; //テーブル名
    //利用者テーブルカラム
    String POS_COL_ID = "id"; //id
    String POS_COL_NAME = "name"; //名前
    String POS_COL_PASS = "password"; //パスワード
    String POS_COL_CREATED_AT = "created_at"; //登録日時

    //トピックテーブル
    String TABLE_TOP = "topics"; //テーブル名
    //トピックテーブルカラム
    String TOP_COL_ID = "id"; //id
    String TOP_COL_POS = "post_id"; //トピックを作成した利用者のid
    String TOP_COL_TITLE = "title"; //トピックのタイトル
    String TOP_COL_CREATED_AT = "created_at"; //作成日時

    //コメントテーブル
    String TABLE_COM = "comments"; //テーブル名
    //コメントテーブルカラム
    String COM_COL_ID = "id"; //id
    String COM_COL_POS = "post_id"; //コメントを作成した利用者のid
    String COM_COL_TOP = "topic_id"; //コメントされたトピックのid
    String COM_COL_MSG  = "msg"; //コメントの内容
    String COM_COL_CREATED_AT = "created_at"; //作成日時

    //Entity名
    String ENTITY_POS = "post"; //利用者
    String ENTITY_TOP = "topic"; //トピック
    String ENTITY_COM = "comment"; //コメント

    //JPQL内パラメータ
    String JPQL_PARM_NAME = "name"; //名前
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_POST = "post"; //

    //NamedQueryの nameとquery
    //全ての利用者をidの降順に取得する
    String Q_POS_GET_ALL = ENTITY_POS + ".getAll"; //name
    String Q_POS_GET_ALL_DEF = "SELECT e FROM Post AS e ORDER BY e.id DESC"; //query
    //全ての利用者の件数を取得する
    String Q_POS_COUNT = ENTITY_POS + ".count";
    String Q_POS_COUNT_DEF = "SELECT COUNT(e) FROM Post AS e";
    //名前とハッシュ化済パスワードを条件に利用者を取得する
    String Q_POS_GET_BY_NAME_AND_PASS = ENTITY_POS + ".getByNameAndPass";
    String Q_POS_GET_BY_NAME_AND_PASS_DEF = "SELECT e FROM Post AS e WHERE e.name = :" + JPQL_PARM_NAME + " AND e.password = :" + JPQL_PARM_PASSWORD;

    //指定した社員番号を保持する利用者の件数を取得する
    String Q_POS_COUNT_REGISTERED_BY_NAME = ENTITY_POS + ".countRegisteredByNmae";
    String Q_POS_COUNT_REGISTERED_BY_NAME_DEF = "SELECT COUNT(e) FROM Post AS e WHERE e.name = :" + JPQL_PARM_NAME;

    //全てのトピックをidの降順に取得する
    String Q_TOP_GET_ALL = ENTITY_TOP + ".getAll";
    String Q_TOP_GET_ALL_DEF = "SELECT r FROM Topic AS r ORDER BY r.id DESC";
    //全てのトピックの件数を取得する
    String Q_TOP_COUNT = ENTITY_TOP + ".count";
    String Q_TOP_COUNT_DEF = "SELECT COUNT(r) FROM Topic AS r";
    //全てのコメントをidの降順に取得する
    String Q_COM_GET_ALL = ENTITY_COM + ".getAll";
    String Q_COM_GET_ALL_DEF = "SELECT r FROM Comment AS r ORDER BY r.id DESC";
    //全てのコメントの件数を取得する
    String Q_COM_COUNT = ENTITY_COM + ".count";
    String Q_COM_COUNT_DEF = "SELECT COUNT(r) FROM Comment AS r";
}