package taweryawer.entities.enums;

public enum OrderStatus {
    NEW {
        @Override
        public String toString() {
            return "Ще не оформлено";
        }
    },
    WAITING {
        @Override
        public String toString() {
            return "Очікує підтвердження оператором⏳";
        }
    },
    CONFIRMED {
        @Override
        public String toString() {
            return "Підтверджено оператором✅";
        }
    },
    CANCELED {
        @Override
        public String toString() {
            return "Скасовано оператором❌";
        }
    },
    AWAITING_LIQPAY_PAYMENT {
        @Override
        public String toString() {
            return "Очікується оплата через Liqpay\uD83D\uDCB3";
        }
    },
    CONFIRMED_LIQPAY_PAYMENT {
        @Override
        public String toString() {
            return "Замовлення оплачено за допомогою Liqpay, очікує підтвердження оператором.⏳";
        }
    }
}
