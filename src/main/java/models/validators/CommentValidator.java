package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.CommentView;
import constants.MessageConst;

/**
 * コメントインスタンスに設定されている値のバリデーションを行うクラス
 */
public class CommentValidator {

    /**
     * コメントインスタンスの各項目についてバリデーションを行う
     * @param rv コメントインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(CommentView rv) {
        List<String> errors = new ArrayList<String>();

        //内容のチェック
        String titleError = validateTitle(rv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        return errors;
    }

    /**
     * コメント内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title 内容
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOCOMMENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}