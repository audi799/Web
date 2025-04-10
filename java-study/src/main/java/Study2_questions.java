public class Study2_questions {
    public static void main(String[] args) {
        System.out.println(filterMessage("아니 우리 정글 ㅈㄴ")); // "아니 우리 정글 **"
        System.out.println(toISODate(587, 6, 13)); //"0587-06-13"
        System.out.println(toISODate(1999, 18, 6)); // null
        System.out.println(removeConsecutiveSpaces("   Hello     nice  to    see    you    "));
        System.out.println(toCamelCase("으아아악hello바나나bye애플orange phone"));
        System.out.println(toCamelCase("Student Name Array"));
        System.out.println(toCamelCase("안녕? Article count가 필요해"));
    }

    public static String removeConsecutiveSpaces(String input) {
        // 전달된 문자열 input에서 선/후행 공백을 모두 제거하고, 연속된 공백을 모두 단일 공백으로 치환하여 반환하는 메서드 removeConsecutiveSpaces를 완성하세요
        // input이 "   Hello     nice  to    see    you    " 일때 반환값은 "Hello nice to see you"
        // 만약에 input이 null이라면 빈문자열을 반환하세요.
        if (input == null) {
            return "";
        }

        String beforeChar = "";
        String convertData = "";
        char[] data = input.stripLeading().stripTrailing().toCharArray();

        for (int i = 0; i < data.length; i++) {
            if (data[i] == ' ') {
                if (beforeChar.equals(" ")) {
                    continue;
                }
            }
            convertData += data[i];
            beforeChar = String.valueOf(data[i]);
        }

        return convertData;
    }

    public static String filterMessage(String message) {
        // 전달된 문자열 message 에서 아래에 주어진 문자열 배열이 가지는 인자를 모두 각 인자가 가지는 길이에 맞는 에스터리스크(*)로 치환한 문자열로 반환하는 메서드 filterMessage 메서드를 완성하세요.
        // (가령, "ㄱㅁㅎㄹ" => "****")
        final String[] slangs = {"ㅎㅍ", "ㅈㄴ", "ㅎㄴ", "ㅅㅁㅌ", "ㅍㄱㅁㅎ"};
        String result = "";
        String starOfCount = "";

        for (int i = 0; i < slangs.length; i++) {
            if (message.contains(slangs[i])) {
                for (int j = 0; j < slangs.length / 2; j++) {
                    starOfCount += "*";
                }
                result = message.replace(slangs[i], starOfCount);
            }
        }

        return result;
    }

    public static String toISODate(int year, int month, int day) {
        // 정수인 년, 월, 일이 주어지면 ISO 형식을 가지는 날짜(yyyy-MM-dd) 문자열을 반환하는 메서드 toIsoDate 를 완성하세요.
        // 각 매개변수 year, month, day 에 전달할 수 있는 인자의 범위에 대한 제한은 없으며 유효한 값은 각각 0~9999, 1~12, 1~31 임으로, 그 외의 값이 전달 되었을 때에는 null을 반환하세요.
        // 단, 윤년, 윤달 및 짧은달, 긴달은 고려하지 않아도 되고, 년도는 네자리, 월, 일은 두자리로 맞추되 자리가 부족할 경우 자리수를 맞춰 앞자리에 "0"으로 채워주세요.
        String strYear = "";
        String strMonth = "";
        String strDay = "";

        if (year < 0 || year > 9999) {
            return null;
        }
        if (month < 0 || month > 12) {
            return null;
        }
        if (day < 0 || day > 31) {
            return null;
        }

        if (String.valueOf(year).length() != 4) {
            strYear = String.valueOf(year);
            strYear = String.format("%4s", strYear);
            strYear = strYear.replace(" ", "0");
        } else {
            strYear = String.valueOf(year);
        }

        if (String.valueOf(month).length() != 2) {
            strMonth = String.valueOf(month);
            strMonth = String.format("%2s", strMonth);
            strMonth = strMonth.replace(" ", "0");
        } else {
            strMonth = String.valueOf(month);
        }

        if (String.valueOf(day).length() != 2) {
            strDay = String.valueOf(day);
            strDay = String.format("%2s", strDay);
            strDay = strDay.replace(" ", "0");
        } else {
            strDay = String.valueOf(day);
        }

        return String.format("%4s-%2s-%2s", strYear, strMonth, strDay);
    }

    public static String toCamelCase(String input) {
        // 전달된 문자열 input을 카멜 케이스화하여 반환하는 메서드 toCamelCase를 완성하세요. 단, 이가 가지는 내용 중 영어 알파벳이 아닌 내용은 반환 값에서 제외하세요. 구분 대상으로 하는 값은 공백( ) 입니다.
        // 가령 input 의 내용이 "Student Name Array" 라면, 반환값은 "studentNameArray"여야 합니다.
        // 마찬가지로 input이 "안녕? Article count가 필요해"라면, 반환값은 "articleCount"여야 합니다.
        // 당연히 인적 사고로 단어간 구분이 가능하더라도 공백이 없다면 케이싱되지 않아도 좋습니다. 가령 "으아아악hello바나나bye애플orange"에 대한 반환값은 중간에 공백이 없으므로 "hellobyeorange"이면 됩니다.
        String result = "";
        String beforeChar = "";
        String convertDate = "";
        for (int i = 0; i < input.length(); i++) {
            if (Character.isLowerCase(input.charAt(i)) || Character.isUpperCase(input.charAt(i)) || input.charAt(i) == ' ') {
                if (input.charAt(i) == ' ' && beforeChar.equals(" ")) {
                    continue;
                }
                if (input.charAt(i) != ' ' && beforeChar.equals(" ")) {
                    convertDate += String.valueOf(input.charAt(i)).toUpperCase();
                    beforeChar = String.valueOf(input.charAt(i));
                    continue;
                }
                if (input.charAt(i) == ' ') {
                    beforeChar = String.valueOf(input.charAt(i));
                    continue;
                }

                convertDate += input.charAt(i);
            }
            beforeChar = String.valueOf(input.charAt(i));
        }

        char[] convertChar = convertDate.toCharArray();
        convertChar[0] = Character.toLowerCase(convertChar[0]);

        return String.valueOf(convertChar);
    }

}
