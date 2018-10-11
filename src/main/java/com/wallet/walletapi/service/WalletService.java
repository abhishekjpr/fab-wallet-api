package com.wallet.walletapi.service;

import com.wallet.walletapi.model.Transactions;
import com.wallet.walletapi.model.UserRequest;

import java.util.List;

public interface WalletService {

    boolean createAccount(UserRequest userRequest);

    boolean updateBalance(Integer userId, Integer amount);

    Float checkBalance(Integer userId);

    List<Transactions> getAllTransactions(String date);

    boolean transferFunds(Integer fromUser, Integer toUser, Integer amount);
}
