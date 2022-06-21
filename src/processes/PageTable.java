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
public class PageTable {
    private ArrayList<Page> pages;
    private int size;
    
    public PageTable(int size){
        this.size = size;
        pages = new ArrayList<Page>(size);
    }

    public int getSize() {
        return size;
    }
    
    public void addPage(Page page){
        pages.add(page);
    }
    
    public Page getPage(int index){
        return pages.get(index);
    }
}
