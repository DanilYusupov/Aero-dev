package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.Company;

import java.util.List;

public interface CompanyDao {

    List<Company> getTopThree();
    

}
