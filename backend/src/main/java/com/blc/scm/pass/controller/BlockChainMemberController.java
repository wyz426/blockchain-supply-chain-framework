package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.services.BlockChainMemberService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blockchain/member")
public class BlockChainMemberController {
    private final BlockChainMemberService blockChainMemberService;

    @Autowired
    public BlockChainMemberController(BlockChainMemberService blockChainMemberService) {
        this.blockChainMemberService = blockChainMemberService;
    }


    /**
     * Get a consortium member by ID.
     *
     * @param id the ID of the consortium member
     * @return the consortium member with the specified ID
     */
    @GetMapping("/{id}")
    public BlockChainMember getConsortiumMemberById(@PathVariable("id") Integer id) {
        return blockChainMemberService.getById(id);
    }

    /**
     * Get all consortium members.
     *
     * @return a list of all consortium members
     */
    @GetMapping("/")
    public List<BlockChainMember> getAllConsortiumMembers() {
        return blockChainMemberService.list();
    }

    @GetMapping("/select/{blockchainName}")
    public List<BlockChainMember> getAllByBlockChain(@PathVariable("blockchainName") String blockchainName) {
        return blockChainMemberService.getByBlockChainName(blockchainName);
    }

    /**
     * Create a new consortium member.
     *
     * @param blockChainMember the consortium member to create
     * @return the created consortium member
     */
    @PostMapping("/")
    public BlockChainMember createConsortiumMember(@RequestBody BlockChainMember blockChainMember) {
        blockChainMemberService.save(blockChainMember);
        return blockChainMember;
    }

    /**
     * Update an existing consortium member.
     *
     * @param id               the ID of the consortium member to update
     * @param blockChainMember the updated consortium member data
     * @return the updated consortium member
     */
    @PutMapping("/{id}")
    public BlockChainMember updateConsortiumMember(@PathVariable("id") Integer id, @RequestBody BlockChainMember blockChainMember) {
        blockChainMember.setId(id);
        blockChainMemberService.updateById(blockChainMember);
        return blockChainMember;
    }

    /**
     * Delete a consortium member by ID.
     *
     * @param id the ID of the consortium member to delete
     */
    @DeleteMapping("/{id}")
    public void deleteConsortiumMember(@PathVariable("id") Integer id) {
        blockChainMemberService.removeById(id);
    }

    @GetMapping("/name/{name}")
    public BlockChainMember getConsortiumMemberByName(@PathVariable("name") String name) {
        return blockChainMemberService.getByName(name);
    }

    @DeleteMapping("/name/{name}")
    public void deleteConsortiumMemberByName(@PathVariable("name") String name){
        blockChainMemberService.deleteByName(name);
    }
}
