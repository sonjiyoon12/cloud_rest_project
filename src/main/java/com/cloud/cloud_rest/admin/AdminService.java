package com.cloud.cloud_rest.admin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.AuthorizationUtil;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import com.cloud.cloud_rest.corp.*;
import com.cloud.cloud_rest.loginhistory.LoginHistory;
import com.cloud.cloud_rest.loginhistory.LoginHistoryRepository;
import com.cloud.cloud_rest.loginhistory.LoginHistoryResponse;
import com.cloud.cloud_rest.loginhistory.TodayResponse;
import com.cloud.cloud_rest.report.Report;
import com.cloud.cloud_rest.report.ReportRepository;
import com.cloud.cloud_rest.report.ReportRequestDto;
import com.cloud.cloud_rest.report.ReportStatus;
import com.cloud.cloud_rest.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CorpRepository corpRepository;
    private final CorpService corpService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginHistoryRepository loginHistoryRepository;
    private final ReportRepository reportRepository;


    @Transactional
    public AdminResponse.SaveDTO save(AdminRequest.SaveDTO saveDTO,SessionUser sessionUser){
        String encodePassword = passwordEncoder.encode(saveDTO.getPassword());

        if(userRepository.existsLoginId(saveDTO.getLoginId())){
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        AuthorizationUtil.validateAdminAccess(sessionUser);

        User admin = saveDTO.toEntity(encodePassword, Role.ADMIN);
        userRepository.save(admin);
        return new AdminResponse.SaveDTO(admin);
    }

    @Transactional
    public String login(AdminRequest.LoginDTO loginDTO){
        User user = userService.getLoginId(loginDTO.getLoginId());
        
        if(!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
            throw new Exception401("서로 비밀번호가 일치하지 않습니다");
        }

        if(user.getRole() != Role.ADMIN){
            throw  new Exception403("관리자 유저가 아닙니다");
        }

        return JwtUtil.createToken(user);
    }

    // 유저의 상세 정보
    public AdminResponse.UserDTO findUserById(Long userId, SessionUser sessionUser) {

        AuthorizationUtil.validateAdminAccess(sessionUser);

        User user = userService.getUserId(userId);
        return new AdminResponse.UserDTO(user);
    }

    // 유저들의 모든 정보 보기
    public List<AdminResponse.UserDTO> adminList(SessionUser sessionUser) {

        AuthorizationUtil.validateAdminAccess(sessionUser);

        List<User> users = userRepository.findAllByRole(Role.USER);
        return users.stream()
                .map(AdminResponse.UserDTO::new)
                .collect(Collectors.toList());
    }

    // 현재 까지 로그인 중인 유저들의 수
    public List<LoginHistoryResponse.LoginDTO> findByLoginUserAll(SessionUser sessionUser){

        AuthorizationUtil.validateAdminAccess(sessionUser);

        List<LoginHistory> histories = loginHistoryRepository.findAll();
        return histories.stream()
                .map(LoginHistoryResponse.LoginDTO::new)
                .collect(Collectors.toList());
    }

    // 오늘 기준으로 로그인 한 유저들의 수
    public TodayResponse.TodayLoginResponse nowLoginUserAll(SessionUser sessionUser){

        AuthorizationUtil.validateAdminAccess(sessionUser);

        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        Timestamp todayStartTimestamp = Timestamp.valueOf(todayStart);

        List<LoginHistory> histories = loginHistoryRepository.findAllByLoginTimeAfter(todayStartTimestamp);
        List<LoginHistoryResponse.LoginDTO> dto = histories.stream()
                .map(LoginHistoryResponse.LoginDTO::new)
                .collect(Collectors.toList());
        return new TodayResponse.TodayLoginResponse(dto.size(),dto);
    }

    // 오늘 회원가입 한 유저들 목록
    public TodayResponse.TodaySignResponse getTodaySignupUsers(SessionUser sessionUser) {
        AuthorizationUtil.validateAdminAccess(sessionUser);

        Timestamp todayStart = Timestamp.valueOf(LocalDate.now().atStartOfDay());

        List<User> users = userRepository.findAllByCreatedAtAfter(todayStart);
        List<UserResponse.SaveDTO> saveDTOS = users.stream()
                .map(UserResponse.SaveDTO::new)
                .collect(Collectors.toList());

        return new TodayResponse.TodaySignResponse(saveDTOS.size(), saveDTOS);
    }

    // 승인 되지 않은 기업 리스트 확인 하기
    public List<CorpResponse.CorpDTO> corpPendingList(SessionUser sessionUser){
        AuthorizationUtil.validateAdminAccess(sessionUser);
        List<Corp> corp = corpRepository.findAll();
        List<CorpResponse.CorpDTO> dto = corp.stream()
                .filter(corp1 -> corp1.getCorpStatus() == CorpStatus.PENDING)
                .map(CorpResponse.CorpDTO::new)
                .toList();
        return dto;
    }

    // 승인 된 기업들의 정보
    public List<CorpResponse.CorpDTO> corpApprovedList(SessionUser sessionUser){
        AuthorizationUtil.validateAdminAccess(sessionUser);
        List<Corp> corp = corpRepository.findAll();
        List<CorpResponse.CorpDTO> dto = corp.stream()
                .filter(corp1 -> corp1.getCorpStatus() == CorpStatus.APPROVED)
                .map(CorpResponse.CorpDTO::new)
                .toList();
        return dto;
    }

    // 승인 승인거부된 기업들의 정보
    public List<CorpResponse.CorpDTO> corpREJECTEDList(SessionUser sessionUser){
        AuthorizationUtil.validateAdminAccess(sessionUser);
        List<Corp> corp = corpRepository.findAll();
        List<CorpResponse.CorpDTO> dto = corp.stream()
                .filter(corp1 -> corp1.getCorpStatus() == CorpStatus.REJECTED)
                .map(CorpResponse.CorpDTO::new)
                .toList();
        return dto;
    }

    // 기업 정보 승인 하기
    @Transactional
    public CorpResponse.CorpDTO corpApproved(Long corpId,SessionUser sessionUser){
        AuthorizationUtil.validateAdminAccess(sessionUser);
        Corp corp = corpService.getCorpId(corpId);
        if(corp.getCorpStatus().equals(CorpStatus.APPROVED)){
            throw new Exception403("이미 승인된 기업입니다");
        }
        corp.setCorpStatus(CorpStatus.APPROVED);
        return new CorpResponse.CorpDTO(corp);
    }

    // 일반 유저 admin 유저로 승인 하기
    @Transactional
    public AdminResponse.SaveDTO userAdvancement(Long userId,SessionUser sessionUser){
        AuthorizationUtil.validateAdminAccess(sessionUser);
        User user = userService.getUserId(userId);
        if(user.getRole().equals(Role.ADMIN)){
            throw new Exception403("이미 어드민 권한 부여자입니다");
        }
        user.setRole(Role.ADMIN);
        return new AdminResponse.SaveDTO(user);
    }


    public User getLoginId(String loginId){
        return userRepository.findByUserId(loginId).orElseThrow(() -> new Exception404("해당 유저가 존재하지 않습니다"));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 신고 관리 기능 추가
    // -----------------------------------------------------------------------------------------------------------------

    // 모든 신고 목록을 조회하는 메서드
    public List<AdminResponse.ReportListDTO> getReports(SessionUser sessionUser) {
        AuthorizationUtil.validateAdminAccess(sessionUser);
        List<Report> reports = reportRepository.findAllWithDetails();
        return AdminResponse.ReportListDTO.fromEntityList(reports);
    }

    // 미처리된 신고 목록을 조회하는 메서드
    public List<AdminResponse.ReportListDTO> getPendingReports(SessionUser sessionUser) {
        AuthorizationUtil.validateAdminAccess(sessionUser);
        List<Report> reports = reportRepository.findByStatus(ReportStatus.PENDING);
        return AdminResponse.ReportListDTO.fromEntityList(reports);
    }

    // 특정 신고의 상세 정보를 조회하는 메서드
    public AdminResponse.ReportListDTO getReportById(Long reportId, SessionUser sessionUser) {
        AuthorizationUtil.validateAdminAccess(sessionUser);
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new Exception404("해당 신고를 찾을 수 없습니다."));
        return new AdminResponse.ReportListDTO(report);
    }

    // 신고 상태를 업데이트하는 메서드
    @Transactional
    public AdminResponse.ReportListDTO updateReportStatus(Long reportId, ReportRequestDto.Update update, SessionUser sessionUser) {
        AuthorizationUtil.validateAdminAccess(sessionUser);
        Report report = reportRepository.findByIdWithDetails(reportId)
                .orElseThrow(() -> new Exception404("해당 신고를 찾을 수 없습니다."));

        report.setStatus(update.getStatus());
        report.setReason(update.getReason());
        reportRepository.save(report);
        return new AdminResponse.ReportListDTO(report);
    }

}
