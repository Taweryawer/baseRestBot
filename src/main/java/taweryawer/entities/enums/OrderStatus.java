package taweryawer.entities.enums;

public enum OrderStatus {
    NEW {
        @Override
        public String toString() {
            return "Ще не оформлений";
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
            return "Підтверджений оператором✅";
        }
    },
    CANCELED {
        @Override
        public String toString() {
            return "Скасований оператором❌";
        }
    },
}
