module jmp.app {
    uses com.epam.jmp.service.Service;
    uses com.epam.jmp.service.bank.Bank;
    requires jmp.dto;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jmp.service.api;
    requires jmp.bank.api;
    requires jmp.dao;

    exports com.epam.jmp;
}