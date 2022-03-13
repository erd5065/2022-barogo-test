package exceptions

class Exceptions {
}

class WrongPaymentMethodException(s: String) : RuntimeException(s) {}
class NotSupportedCashSumException(s: String) : RuntimeException(s) {}
class MissingBeverageException(s: String) : RuntimeException(s) {}
class CardNotRecognizedException(s: String) : RuntimeException(s) {}