package from_lab_2;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmployeeCondition {
    @JsonProperty("Obecny")
    PRESENT,

    @JsonProperty("Nieobecny")
    NOT_PRESENT,

    @JsonProperty("Delegacja")
    DELEGATION,

    @JsonProperty("L4")
    SICK_LEAVE;
    public String toString() {
        switch (this) {
            case PRESENT:
                return "Obecny";
            case NOT_PRESENT:
                return "Nieobecny";
            case DELEGATION:
                return "Delegacja";
            case SICK_LEAVE:
                return "L4";
            default:
                return "";
        }
    }
}

