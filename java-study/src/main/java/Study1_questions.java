public class Study1_questions {
    public static void main(String[] args) {
        System.out.println(sum(new int[]{3, -1, 6, 10, -9})); // 9
        System.out.println(sum(null)); // 0
        System.out.println(areCapitals(new char[]{'H', 'E', 'L', 'L', 'O'})); //true
        System.out.println(areCapitals(new char[]{'H', 'i'})); // false
        String[] filterNames = filterNames(new String[]{"김갑수", "이춘자", "최길수"}, new int[]{67, 83, 50});

        for (String name : filterNames) {
            System.out.println(name);
        } // 이춘자

        System.out.println(playBlackjack(
                new char[]{'J', 'A'},
                new char[]{'4', 'J', 'Q'}
        )); // '0'

        int score = validatePassword("Test1234!@//");
        System.out.println(score);
    }

    public static int sum(int[] nums) {
        // 전달받은 정수 배열 nums 가 가지는 인자들의 합을 반환하는 메서드 sum을 완성하세요.
        // 단, 매개 변수 nums가 null인 경우 0을 반환하세요.
        // 추가로, nums의 인자의 합이 int 가 가질 수 있는 최소값, 최대값의 범위를 벗어나는 경우는 없습니다.
        int sumOfArray = 0;

        if (nums == null) {
            return 0;
        } else {
            for (int i = 0; i < nums.length; i++) {
                sumOfArray += nums[i];
            }
            return sumOfArray;
        }
    }

    public static boolean areCapitals(char[] cs) {
        // 전달받은 문자 배열 cs 가 가지는 인자들이 모두 알파뱃 대문자인가의 여부를 반환하는 메서드 areCapitals를 완성하세요.
        // 단, 매개변수 cs가 null인 경우 false를 반환하세요.
        // 추가로, cs의 인자가 반드시 알파벳의 범위(a~z, A-Z)안에 있지는 않습니다.
        if (cs == null) {
            return false;
        } else {
            for (int i = 0; i < cs.length; i++) {
                if (Character.isLowerCase(cs[i])) {
                    return false;
                }
            }

//            향상된 for문 사용 시
//            for (char c: cs) {
//                if (c < 65 || c > 90) {
//                    return false;
//                }
//            }
            return true;
        }
    }

    public static String[] filterNames(String[] names, int[] scores) {
        // 매개변수 names 는 응시생의 이름을 순서대로 담은 문자열 배열이고 scores는 점수를 순서대로 담은 정수 배열입니다.
        // 합격을 위한 최소 점수 70점이라고 할 때, 합격한 사람의 이름을 인자로 가지는 문자열 배열을 반환하는 메서드 filterNames를 완성하세요.
        // 단, 매개변수인 names 와 scores 배열의 길이는 1이상이고 항상 같으며 null 이지 않습니다. 점수는 반드시 0점이상 100점 이하의 값만 전달합니다.
        // 합격자가 없는 경우 길이가 0인 문자열 배열을 반환하세요.
        int count = 0;
        for (int i = 0; i < names.length; i++) {
            if (scores[i] >= 70) {
                count++;
            }
        }

        if (count == 0) {
            return new String[0];
        }

        String[] filteredNames = new String[count];

        for (int i = 0; i < scores.length; i++) {
            if (scores[i] >= 70) {
                for (int j = 0; j < count; j++) {
                    if (filteredNames[j] == null) {
                        filteredNames[j] = names[i];
                    }
                }
            }
        }
        return filteredNames;
    }

    public static int validatePassword(String password) {
        // 문자열 객체가 가지는 toCharArray() 메서드는 문자열이 가지는 문자를 인자로 가지는 문자 배열(char[])을 반환합니다.
        // 가령, "Hello".toCharArray()는 {'H', 'e', 'l', 'l', 'o'}을 반환합니다.
        char[] cs = password.toCharArray();
        // 전달받은 비밀번호 문자열에 대해 보안상 점수(0~5점, 취약~안전)을 반환하는 로직을 담은 메서드 validatePassword 를 작성하세요
        // - 비밀번호는 4자 이상, 알파벳을 포함해야 합니다. 특수 문자는 필수가 아니지만 ! @ # $ 외의 특수문자를 사용하면 안됩니다. 해당 규격을 벗어날 경우 -1점을 반환하세요.
        // - 길이(-1~2점):
        //   * 3자 이하: 올바르지 않은 형식임으로 -1점 반환
        //   * 4자 이상 ~ 6자 미만: 0점
        //   * 6자 이상 ~ 8자 미만: 1점
        //   * 8자 이상: 2점
        // - 영어 대/소문자(0~1점):
        //   * 영어 없음: 올바르지 않은 형식임으로 -1점 반환
        //   * 영어 소문자만/대문자만 있음: 0점
        //   * 영어 대소문자가 섞여있음: 1점
        // - 특수 문자(0~2점):
        //   * 허용되는 툭수문자: ! @ # $
        //   * 허용되지 않는 특수문자가 포함되면: 올바르지 않은 형식임으로 -1점 반환
        //   * 허용되는 특수문자 0자: 0점
        //   * 허용되는 특수문자 1자: 1점
        //   * 허용되는 특수문자 2자 이상: 2점

        // 총 자릿수 확인
        int point = 0;
        if (cs.length < 4) {
            point -= 1;
        } else if (cs.length >= 8) {
            point += 2;
        } else if (cs.length >= 6) {
            point += 1;
        }

        // 영어 대/소문자 확인
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] >= 'A' && cs[i] <= 'Z') {
                hasUpperCase = true;
            }
            if (cs[i] >= 'a' && cs[i] <= 'z') {
                hasLowerCase = true;
            }
        }
        if (hasUpperCase && hasLowerCase) {
            point += 1;
        } else if (!hasUpperCase && !hasLowerCase) {
            point -= 1;
        }

        // 특수 문자 확인
        int includeCount = 0;
        int excludeCount = 0;
        char[] disAgreedChars = {34, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 92, 93, 94, 95, 96, 123, 124, 125, 126};
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == '!' || cs[i] == '@' || cs[i] == '#' || cs[i] == '$') {
                includeCount++;
            }
            for (int j = 0; j < disAgreedChars.length; j++) {
                if (cs[i] == disAgreedChars[j]) {
                    excludeCount++;
                }
            }
        }
        if (includeCount == 1) {
            point += 1;
        } else if (includeCount >= 2) {
            point += 2;
        }
        if (excludeCount >= 1) {
            point -= 1;
        }

        return point;
    }

    //    System.out.println(playBlackjack(
//            new char[]{'J', 'A'},
//            new char[]{'4','J','Q'}
//    )); // '0'
    public static char playBlackjack(char[] dealerDecks, char[] playerDecks) {
        // 블랙잭은 2부터 9까지의 숫자와 J, Q, K 및 A의 카드 네장씩(클로버, 하트, 다이아몬드, 스페이드) 으로 이루어진 조합 속에서 카드를 모아 그 합이 21에 최대한 가까운 숫자를 만드는 사람이 이기는 게임입니다.
        // 단, J, Q, K는 10으로 산정하고 A는 경우에 따라 유리하기 1 혹은 11로 산정할 수 있습니다.
        // 조합의 합이 21을 초과할 경우 'Busted' 되게 되어 상대방의 조합의 합과 관계 없이 패하게 됩니다.
        // 매개변수 dealerDecks 과 playerDecks 은 각각 상대방과 본인이 가지고 있는 카드 조합을 나타내는 문자를 인자로 가지는 문자 배열입니다. 단, 카드의 모양은 고려하지 않으며 카드의 값은 각 '1'부터 '9', 'J', 'Q', 'K', 'A'인 문자로 이루어져 있습니다.
        // 위 규칙하에 딜러의 승리일 경우 '0'을, 본인(플레이어)의 승리일 경우 'P'를, 무승부일 경우 '\0'을 반환하는 함수 playBlackjack 완성하세요.
        // 단, 매개변수는 null 이지 않으며 각 배열의 길이는 반드시 2이상입니다.

        int dealerSum = 0;
        int playerSum = 0;
        int addValue = 0;
        int cardAValueOfDealer = 0;
        int cardAValueOfPlayer = 0;
        boolean hasDealerA = false;
        boolean hasPlayerA = false;

        for (int i = 0; i < dealerDecks.length; i++) {
            if (dealerDecks[i] == 'J' || dealerDecks[i] == 'Q' || dealerDecks[i] == 'K') {
                addValue = 10;
            } else if (dealerDecks[i] == 'A') {
                hasDealerA = true;
            } else {
                addValue = dealerDecks[i];
            }
            dealerSum += addValue;
        }

        for (int i = 0; i < playerDecks.length; i++) {
            if (playerDecks[i] == 'J' || playerDecks[i] == 'Q' || playerDecks[i] == 'K') {
                addValue = 10;
            } else if (playerDecks[i] == 'A') {
                hasPlayerA = true;
            } else {
                addValue = playerDecks[i];
            }
            playerSum += addValue;
        }

        if (hasDealerA) {
            if (21 - dealerSum > 0) {
                cardAValueOfDealer = 21 - dealerSum;
            } else if (21 - dealerSum <= 0) {
                cardAValueOfPlayer = 1;
            }
        }

        if (hasPlayerA) {
            if (21 - playerSum > 0) {
                cardAValueOfPlayer = 21 - playerSum;
            } else if (21 - playerSum <= 0) {
                cardAValueOfPlayer = 1;
            }
        }

        dealerSum += cardAValueOfDealer;
        playerSum += cardAValueOfPlayer;

        if (Math.abs(21 - dealerSum) < Math.abs(21 - playerSum)) {
            return '0';
        } else if (Math.abs(21 - dealerSum) > Math.abs(21 - playerSum)) {
            return 'P';
        } else {
            return '\0';
        }
    }
}