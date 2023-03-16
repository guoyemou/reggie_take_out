package com.yezi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yezi.entity.AddressBook;
import com.yezi.mapper.AddressBookMapper;
import com.yezi.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
