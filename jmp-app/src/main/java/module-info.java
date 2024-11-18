import com.epam.jmp.service.bank.Bank;

module jmp.app {
    uses com.epam.jmp.service.Service;
    uses Bank;
    requires jmp.dto;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jmp.service.api;
    requires jmp.bank.api;
//
    exports com.epam.jmp;
}