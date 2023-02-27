package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Post;

/**
 * 利用者データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class PostConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev PostViewのインスタンス
     * @return Postのインスタンス
     */
    public static Post toModel(PostView ev) {

        return new Post(
                ev.getId(),
                ev.getName(),
                ev.getPassword(),
                ev.getCreatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Postのインスタンス
     * @return PostViewのインスタンス
     */
    public static PostView toView(Post e) {

        if(e == null) {
            return null;
        }

        return new PostView(
                e.getId(),
                e.getName(),
                e.getPassword(),
                e.getCreatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<PostView> toViewList(List<Post> list) {
        List<PostView> evs = new ArrayList<>();

        for (Post e : list) {
            evs.add(toView(e));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Post e, PostView ev) {
        e.setId(ev.getId());
        e.setName(ev.getName());
        e.setPassword(ev.getPassword());
        e.setCreatedAt(ev.getCreatedAt());
    }

}