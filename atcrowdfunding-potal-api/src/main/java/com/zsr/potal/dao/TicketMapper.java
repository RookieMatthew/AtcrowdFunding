package com.zsr.potal.dao;

import com.zsr.bean.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    List<Ticket> selectAll();

    int updateByPrimaryKey(Ticket record);

    Ticket getTicketByMemberId(Integer memberid);

    int addTicket(Ticket ticket);

    int updatePstep(Ticket ticket);

    int updatePstepAndPiidAndAuthcode(Ticket ticket);
}