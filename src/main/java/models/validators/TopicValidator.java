package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.TopicView;
import constants.MessageConst;

/**
 * トピックインスタンスに設定されている値のバリデーションを行うクラス
 */
public class TopicValidator {

    /**
     * トピックインスタンスの各項目についてバリデーションを行う
     * @param rv トピックインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(TopicView rv) {
        List<String> errors = new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(rv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        return errors;
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOTITLE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}