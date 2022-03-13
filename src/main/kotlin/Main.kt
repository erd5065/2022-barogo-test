import exceptions.CardNotRecognizedException
import exceptions.MissingBeverageException
import exceptions.NotSupportedCashSumException
import exceptions.WrongPaymentMethodException
import java.util.*

// 공통변수 모음집. 단, java로 만든다면 타입 및 지역변수 구분도 고려할 것.
var paymentMethod: String = ""
var cash: Int = 0
var card: Int = 0
var beverage: String = ""
var sc = Scanner(System.`in`)
// ---------------------------

//결제수단을 판별하는 함수
fun paymentMethodChecker(paymentMethod: String): String {
    when(paymentMethod) {
        "card" -> { return paymentMethod }
        "cash" -> { return paymentMethod }
        else -> {
            throw WrongPaymentMethodException("결제수단을 넣지 않았습니다.")
        }
    }
}

//카드결제 과정에서 선택가능한 음료를 제약하는 함수
fun cardProcess(input: String): String {
    when(input) {
        "콜라" -> { return input }
        "물"  -> { return input }
        "커피" -> { return input }
        else ->{
            throw MissingBeverageException("죄송합니다. 현재 콜라,물,커피만 제공 가능합니다.")
        }
    }
}

//현금결제시, 금액을 계산하는 함수, 물건판별 포함, 원래 cardProcess와 병합 가능하나 분리.
fun cashCalculate(input: String): Boolean {
    when(input) {
        "콜라" -> { cash-=1100; return true }
        "물"  -> { cash-=600; return true  }
        "커피" -> { cash-=700; return true  }
        else ->{
            throw MissingBeverageException("죄송합니다. 현재 콜라,물,커피만 제공 가능합니다.")
        }
    }
}

//입력된 현금을 판별하는 함수.
fun cashChecker(input: Int): Int {
    when(input) {
        100,500,1000,5000,10000 -> { return input }
        else -> throw NotSupportedCashSumException("일반적인 단일 권종만 입력 가능합니다. 여러 현금투입내역을 정산하는 기능은 추후 업데이트 예정입니다.")
    }
}

fun main(args: Array<String>) {
    /**
     * input1 ?
     * 사용자 결제수단.
     * 현금: 100,500,1000,5000,10000 + 현금없는데 누르는 null
     * 카드: 잔금(0), 잔금(모자라거나 없음, 결제불가.)
     * -> 즉, switch나 when 문으로 분기 가능.
     * 자체 제약사항 추가. 들어온 현금을 더해서
     * 합산하거나 하는 기능까지 추가하면 2시간 미만 사용 권장에 걸리므로 생략.
     */
    println("card나 cash를 투입해 주세요. 입력하지 않을 시 진행할 수 없습니다.")
    paymentMethod = paymentMethodChecker(sc.nextLine())
    println(paymentMethod)

    when(paymentMethod) {
        "card" -> {
            println("카드를 기기에 넣어주신 후, 1번을 눌러주세요.")
            if(!sc.nextInt().equals(1)) {
                throw CardNotRecognizedException("카드가 인식되지 않았습니다.")
            }
            var a = true
            while(a){
                println("구매 가능한 음료수를 선택해 주세요. | 콜라: 1100 / 물: 600 / 커피: 700")
                println(cardProcess(sc.next()))
                // todo: 여기에 원래는 인증 및 승인실패 여부를 추가할 예정.
                println("더 구매하시려면 1번을 눌러주세요. 결제를 종료하시려면 아무 키나 눌러주세요.")
                if(!sc.nextInt().equals(1)) {
                    break
                }
            }
            println("이용해 주셔서 감사합니다.")
        }
        "cash" -> {
            /**
             * switch(case 현금) {
             * 현금 투입 후 물건을 고른다.
             * if(물건값총합>현금)
             * throw exception("돈이 부족합니다.") -> setTimeOut(3)초후 잔돈 반납.
             * else
             * then 물건 출력, 물건만큼 돈을 깐다.
             * ... 반복.
             * -> setTimeOut(3)초후 잔돈 반납. (... 이건 공통함수로 묶거나 try catch로 가능할 듯.)
             * }
             * -> 이 구현시점에서 20분 소모, 2시간 제한상
             * 재고가 없는 경우와 setTimeOut() 사용 생략.
             */
            println("현금을 투입해 주세요. 현재 버전0.0.1에서 현금은 100원, 500원, 1000원, 5000원, 10000원권 단일 권종만 투입 가능합니다.")
            cash = cashChecker(sc.nextInt())
            println(cash)

            while (cash>600) {
                println("구매 가능한 음료수를 선택해 주세요. | 콜라: 1100 / 물: 600 / 커피: 700")
                var ans = cashCalculate(sc.next())
                println(cash)
                if(cash<600){
                    break
                }
                if(ans) {
                    println("더 구매하시려면 1번을 눌러주세요. 거스름돈을 원하시면 아무 키나 눌러주세요.")
                }
                if(!sc.nextInt().equals(1)) {
                    break
                }
            }
            println("거스름돈입니다: ${cash}, 이용해 주셔서 감사합니다.")
        }
    }

    //2. 자판기의 주요 로직을 pseudocode나 다른 언어로 표현할 시 대략적인 구상도.
    /**
     *
     * input1 ?
     * 사용자 결제수단.
     * 현금: 100,500,1000,5000,10000 + 현금없는데 누르는 null
     * 카드: 잔금(0), 잔금(모자라거나 없음, 결제불가.)
     * -> 즉, switch나 when 문으로 분기 가능.
     * 자체 제약사항 추가. 들어온 현금을 더해서
     * 합산하거나 하는 기능까지 추가하면 2시간 미만 사용 권장에 걸리므로 생략.
     *
     * input2 ?
     * 구매 가능한 음료수.
     * 콜라: 1100 / 물: 600 / 커피: 700
     * -> 누를 때마다 결제가 일어나는 구조로 진행.
     *
     * 제외한 Custom Exception:
     * 물건이 걸려서 안나오는 경우와 재고가 없는 경우...
     * -> 물리적인 조건까지 신경쓰면 2시간 조건 초과 예상.
     *
     *
     * if(결제수단도 없이 누른다?)
     * then throw exception("무반응 || 돈읋 넣어주세요")
     * else
     * switch(case 현금) {
     * 현금 투입 후 물건을 고른다.
     * if(물건값총합>현금)
     * throw exception("돈이 부족합니다.") -> setTimeOut(3)초후 잔돈 반납.
     * else
     * then 물건 출력, 물건만큼 돈을 깐다.
     * ... 반복.
     * -> -> setTimeOut(3)초후 잔돈 반납. (... 이건 공통함수로 묶거나 try catch로 가능할 듯.)
     *
     * }
     *
     * switch(case 카드) {
     * 카드를 기기에 꽂는다.
     * 카드는 체크카드(삽입), 신용카드(긁고 상품선택1), 교통카드(접촉후 상품선택1)가 있고
     * PG사에 따른 client-side, server-side 스타일에 따른 인증, 승인과정 flow가 있는데...
     * 그걸 다하면 권장시간 2시간을 명백히 넘기므로 생략합니다.
     * 카드인식 여부만 설정.
     * + https://www.youtube.com/watch?v=1nlnm_z32v8
     * 영상을 근거로 자판기 카드결제는 물건 단건만 가능조건 확인.
     * if(카드인식여부 false)
     * throw exception("카드가 인식되지 않았. 다른 카드로 시도해 주세요.")
     * else
     * 카드 인증 된거니 물건 선택.
     * ->인증 진행.
     * if(인증실패)
     * throw exception("인증이 실패했습니다. 해당 카드사 또는 pg사에 문의해 주세요.")
     * else {
     * 진행.
     * if(승인실패.) {
     * throw exception("승인이 실패했습니다. 해당 카드사에 문의해 주세요.")
     * } else if(카드 잔액부족 ==true) {
     * throw exception("잔액부족, 승인이 실패했습니다.")
     * } else {
     * 정상적으로 진행. 카드 청구.
     * }
     * }
     * 완료.
     * }
     *
     * println("이용해주셔서 감사합니다.")
     *
     */


}