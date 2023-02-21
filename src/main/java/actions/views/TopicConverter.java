package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Topic;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TopicConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv TopicViewのインスタンス
     * @return Topicのインスタンス
     */
    public static Topic toModel(TopicView rv) {
        return new Topic(
                rv.getId(),
                PostConverter.toModel(rv.getPost()),
                rv.getTitle(),
                rv.getCreatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Topicのインスタンス
     * @return TopicViewのインスタンス
     */
    public static TopicView toView(Topic r) {

        if (r == null) {
            return null;
        }

        return new TopicView(
                r.getId(),
                PostConverter.toView(r.getPost()),
                r.getTitle(),
                r.getCreatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TopicView> toViewList(List<Topic> list) {
        List<TopicView> evs = new ArrayList<>();

        for (Topic r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Topic r, TopicView rv) {
        r.setId(rv.getId());
        r.setPost(PostConverter.toModel(rv.getPost()));
        r.setTitle(rv.getTitle());
        r.setCreatedAt(rv.getCreatedAt());

    }

}