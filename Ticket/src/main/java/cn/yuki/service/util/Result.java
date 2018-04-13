package cn.yuki.service.util;

import java.io.Serializable;

public enum Result implements Serializable{
    Success,
    HaveRegistered,
    HaveValidated,
    NotRegistered,
    NotActivated,
    NotLogin,
    WrongValidateCode,
    WrongPassword,
    WrongAccount,
    OutDatedValidateCode,
    NoEnoughRemains,
    TimeConflict,
    NoOrderFound,
    SeatConflict,
    WrongOrderState,
    HaveDeleted,
    HaveChecked,
    Invalid
}
