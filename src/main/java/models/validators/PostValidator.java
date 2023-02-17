package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.PostView;
import constants.MessageConst;
import services.PostService;

/**
 * 従業員インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class PostValidator {

    /**
     * 利用者インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param ev PostViewのインスタンス
     * @param nameDuplicateCheckFlag 名前の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            PostService service, PostView ev, Boolean nameDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //名前のチェック
        String nameError = validateName(ev.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(ev.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

//    /**
//     * 名前の入力チェックを行い、エラーメッセージを返却
//     * @param service PostServiceのインスタンス
//     * @param name 名前
//     * @param nameDuplicateCheckFlag 名前の重複チェックを実施するかどうか(実施する:true 実施しない:false)
//     * @return エラーメッセージ
//     */
//    private static String validateName(PostService service, String name, Boolean nameDuplicateCheckFlag) {
//
//        //入力値がなければエラーメッセージを返却
//        if (name == null || name.equals("")) {
//            return MessageConst.E_NONAME.getMessage();
//        }
//
//        if (nameDuplicateCheckFlag) {
//            //名前の重複チェックを実施
//
//            long postsCount = isDuplicatePost(service, name);
//
//            //同一名が既に登録されている場合はエラーメッセージを返却
//            if (postsCount > 0) {
//                return MessageConst.E_POS_NAME_EXIST.getMessage();
//            }
//        }
//
//        //エラーがない場合は空文字を返却
//        return "";
//    }
//
//    /**
//     * @param service PostServiceのインスタンス
//     * @param name 名前
//     * @return 利用者テーブルに登録されている同一社員番号のデータの件数
//     */
//    private static long isDuplicatePost(PostService service, String name) {
//
//        long postsCount = service.countByName(name);
//        return postsCount;
//    }

    /**
     * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 名前
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}