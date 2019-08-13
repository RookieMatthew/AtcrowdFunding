package com.zsr.potal.service;

import com.zsr.bean.Ticket;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/11 11:24
 */
public interface TicketService {
    Ticket getTicketByMemberId(Integer memberid);

    int addTicket(Ticket ticket);

    int updatePstep(Ticket ticket);

    int updatePstepAndPiidAndAuthcode(Ticket ticket);
}
