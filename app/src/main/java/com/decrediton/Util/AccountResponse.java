package com.decrediton.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by collins on 12/30/17.
 */
public class AccountResponse {
    public boolean errorOccurred;
    public String errorMessage, currentBlockHash;
    public int count, errorCode, currentBlockHeight;
    public ArrayList<AccountItem> items = new ArrayList<>();
    private AccountResponse(){}
    public static AccountResponse parse(String json) throws JSONException {
        System.out.println("Account JSON: "+json);
        AccountResponse response = new AccountResponse();
        JSONObject obj = new JSONObject(json);
        response.errorOccurred = obj.getBoolean("ErrorOccurred");
        response.errorMessage = obj.getString("ErrorMessage");
        response.errorCode = obj.getInt("ErrorCode");
        if(!response.errorOccurred) {
            response.count = obj.getInt("Count");
            response.currentBlockHeight = obj.getInt("Current_block_height");
            response.currentBlockHash = obj.getString("Current_block_hash");
            JSONArray acc = obj.getJSONArray("Acc");
            for (int i = 0; i < acc.length(); i++) {
                final JSONObject account = acc.getJSONObject(i);
                response.items.add(new AccountItem() {
                    {
                        number = account.getInt("Number");
                        name = account.getString("Name");
                        System.out.println();
                        externalKeyCount = account.getInt("External_key_count");
                        internalKeyCount = account.getInt("Internal_key_count");
                        importedKeyCount = account.getInt("Imported_key_count");
                        JSONObject balanceObj = account.getJSONObject("Balance");
                        balance = new Balance();
                        balance.total = balanceObj.getInt("Total");
                        balance.spendable = balanceObj.getInt("Spendable");
                        balance.immatureReward = balanceObj.getInt("ImmatureReward");
                        balance.immatureStakeGeneration = balanceObj.getInt("ImmatureStakeGeneration");
                        balance.lockedByTickets = balanceObj.getInt("LockedByTickets");
                        balance.votingAuthority = balanceObj.getInt("VotingAuthority");
                        balance.unConfirmed = balanceObj.getInt("UnConfirmed");
                    }
                });
            }
        }
        return response;
    }
    public static class AccountItem{
        public int number, externalKeyCount,internalKeyCount,importedKeyCount;
        public String name;
        public Balance balance;
    }
    public static class Balance{
        public int spendable, total,immatureReward, immatureStakeGeneration,lockedByTickets, votingAuthority, unConfirmed;
    }
}