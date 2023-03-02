package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Comment;

/**
 * コメントデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class CommentConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv CommentViewのインスタンス
     * @return Commentのインスタンス
     */
    public static Comment toModel(CommentView rv) {
        return new Comment(
                rv.getId(),
                PostConverter.toModel(rv.getPost()),
                TopicConverter.toModel(rv.getTopic()),
                rv.getTitle(),
                rv.getCreatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Commentのインスタンス
     * @return CommentViewのインスタンス
     */
    public static CommentView toView(Comment r) {

        if (r == null) {
            return null;
        }

        return new CommentView(
                r.getId(),
                PostConverter.toView(r.getPost()),
                TopicConverter.toView(r.getTopic()),
                r.getTitle(),
                r.getCreatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CommentView> toViewList(List<Comment> list) {
        List<CommentView> evs = new ArrayList<>();

        for (Comment r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Comment r, CommentView rv) {
        r.setId(rv.getId());
        r.setPost(PostConverter.toModel(rv.getPost()));
        r.setTopic(TopicConverter.toModel(rv.getTopic()));
        r.setTitle(rv.getTitle());
        r.setCreatedAt(rv.getCreatedAt());

    }

}