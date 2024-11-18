module jmp.app {
    uses com.epam.jmp.service.Service;
    uses com.epam.jmp.service.Bank;
    requires jmp.dto;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
//
    exports com.epam.jmp;
}