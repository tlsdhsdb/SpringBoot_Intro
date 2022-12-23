package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {

    private MemberRepository memberRepository = new MemoryMemberRepository();

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*
    * 회원가입
    * */
    public Long join(Member member){
        //같은 이름이 있는 중복회원은 안됨
        validateDuplicateMember(member); // 중복회원 검증 로직

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /*
    * 개인 회원 조회
    * */

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
