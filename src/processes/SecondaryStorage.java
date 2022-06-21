/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processes;

import java.util.ArrayList;

/**
 *
 * @author A.Mabkhout
 */
public class SecondaryStorage {
    
    private ArrayList<Object> storage;

    public SecondaryStorage(ArrayList<Object> storage) {
        this.storage = storage;
    }

    public ArrayList<Object> getStorage() {
        return storage;
    }
    
    
}
