package com.zsr.potal.service.impl;

import com.zsr.bean.Ticket;
import com.zsr.potal.dao.TicketMapper;
import com.zsr.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/11 11:24
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Ticket getTicketByMemberId(Integer memberid) {
        return ticketMapper.getTicketByMemberId(memberid);
    }

    @Override
    public int addTicket(Ticket ticket) {
        return ticketMapper.addTicket(ticket);
    }

    @Override
    public int updatePstep(Integer memberid, String pstep) {
        return ticketMapper.updatePstep(memberid,pstep);
    }
}
