package taweryawer.entities.enums;

public enum PaymentMethod {
    CASH {
        @Override
        public String toString() {
            return "Наличными";
        }
    },
    CARD {
        @Override
        public String toString() {
            return "Карта";
        }
    },
    LIQPAY {
        @Override
        public String toString() {
            return "Карта(Liqpay)";
        }
    }
}
