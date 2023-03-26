package com.example.finalprojectrestoran.service.serviceImpl;

import com.example.finalprojectrestoran.config.jwt.JwtUtil;
import com.example.finalprojectrestoran.dto.requests.AnswerRequest;
import com.example.finalprojectrestoran.dto.requests.UserApplicationRequest;
import com.example.finalprojectrestoran.dto.requests.UserRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.UserApplicationResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponse;
import com.example.finalprojectrestoran.dto.responses.UserResponses;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.UserPaginationResponse;
import com.example.finalprojectrestoran.entity.Restaurant;
import com.example.finalprojectrestoran.entity.User;
import com.example.finalprojectrestoran.entity.enums.Role;
import com.example.finalprojectrestoran.exception.AlreadyException;
import com.example.finalprojectrestoran.exception.BadRequestException;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.RestaurantRepository;
import com.example.finalprojectrestoran.repository.UserRepository;
import com.example.finalprojectrestoran.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponses authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()));
        var user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException(String.format("User with email: %s doesn't exists", userRequest.email())));
        var token = jwtUtil.generateToken(user);
        return UserResponses.builder()
                .token(token)
                .build();
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest)  {
        Restaurant restaurant = restaurantRepository.findById(userRequest.restaurantId()).orElseThrow(
                () -> new NotFoundException("Restaurant with id : " + userRequest.restaurantId() + "is not found!"));
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setRole(userRequest.role());
        user.setExperience(userRequest.experience());
        var count = restaurant.getUsers().size();
        if (count > 15) {
            throw new AlreadyException("No vacancy");
        } else if (userRequest.role().equals(Role.ADMIN)) {
            throw new AlreadyException("Already have an admin");
        } else {
            restaurant.addUser(user);
            user.setRestaurant(restaurant);
            userRepository.save(user);
            restaurant.setNumberOfEmployees((byte) ++count);
            restaurantRepository.save(restaurant);
        }
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("Employee with fullName : %s job title: %s " +
                                "successfully saved",
                        user.getFirstName().concat(user.getLastName()), user.getRole())).build();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id : " + id + "is not found"));
        user.getCheques().forEach(cheque -> cheque.getMenuItems().forEach(menuItem -> menuItem.setCheques(null)));
        userRepository.delete(user);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with id : %s is deleted!", id)).build();
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.getUser(id).orElseThrow(
                () -> new NotFoundException("User with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {
        User oldUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id " + id + "is not found!"));
        oldUser.setLastName(userRequest.lastName());
        oldUser.setDateOfBirth(userRequest.dateOfBirth());
        oldUser.setFirstName(userRequest.firstName());
        oldUser.setEmail(userRequest.email());
        oldUser.setPassword(passwordEncoder.encode(userRequest.password()));
        oldUser.setPhoneNumber(userRequest.phoneNumber());
        oldUser.setRole(userRequest.role());
        oldUser.setExperience(userRequest.experience());
        userRepository.save(oldUser);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with name : %s " + "successfully update", userRequest.firstName())).build();

    }

    @Override
    public SimpleResponse application(UserApplicationRequest userApplicationRequest)  {
        var age = LocalDate.now().getYear() - userApplicationRequest.dateOfBirth().getYear();
        User user = new User();
        user.setEmail(userApplicationRequest.email());
        user.setDateOfBirth(userApplicationRequest.dateOfBirth());
        user.setExperience(userApplicationRequest.experience());
        user.setPhoneNumber(userApplicationRequest.phoneNumber());
        user.setLastName(userApplicationRequest.lastName());
        user.setFirstName(userApplicationRequest.firstName());
        user.setPassword(passwordEncoder.encode(userApplicationRequest.password()));
        user.setRole(userApplicationRequest.role());
        if (userApplicationRequest.role().equals(Role.WALTER)) {
            if (age >= 18 && age <= 30) {
                if (userApplicationRequest.experience() >= 1) {
                    userRepository.save(user);
                }else {
                    throw new AlreadyException("Waiter must have at least 1 year experience");
                }
            }else {
                throw new AlreadyException("the waiter's age must be at least 30 and over 18");
            }
        } else if (userApplicationRequest.role().equals(Role.CHEF)) {
            if (age >= 25 && age <= 45) {
                if (userApplicationRequest.experience() >= 2) {
                    userRepository.save(user);
                }else {
                    throw new AlreadyException("Chef must have at least 2 year experience");
                }
            }else {
                throw new AlreadyException("the chef's age must be at least 45 and over 25");
            }
        } else {
            throw new AlreadyException("You incorrectly gave the position Either you gave the admin Well, we already have an admin");
        }
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("Employee with fullName : %s job title: %s " +
                                "successfully saved",
                        userApplicationRequest.firstName().concat(userApplicationRequest.lastName()), userApplicationRequest.role())).build();
    }

    @Override
    public List<UserApplicationResponse> findAllUsersApplications()  {
        return userRepository.findAllByUserResume();
    }

    @Override
    public SimpleResponse answer(AnswerRequest answerRequest)  {
        User user = userRepository.findById(answerRequest.userId()).orElseThrow(
                () -> new NotFoundException("User with id " + answerRequest.userId() + "is not found!"));
        List<User> users = userRepository.users().stream().filter(user1 -> user1.getId().equals(user.getId())).toList();
        if (users.isEmpty()){
            throw new BadRequestException("we already have such an employee");
        }else {
            Restaurant restaurant = restaurantRepository.findRestaurantByName(answerRequest.restaurantName()).orElseThrow(
                    () -> new NotFoundException("Restaurant with name: " + answerRequest.restaurantName() + "is not found!"));
            int size = restaurant.getUsers().size();
            if (answerRequest.message().equalsIgnoreCase("accepted")) {
                if (size > 15) {
                    throw new AlreadyException("no vacancy");
                } else {
                    user.setRestaurant(restaurant);
                    restaurant.addUser(user);
                    userRepository.save(user);
                    restaurant.setNumberOfEmployees((byte) ++size);
                    restaurantRepository.save(restaurant);
                }
            } else if (answerRequest.message().equalsIgnoreCase("did not accept")) {
                userRepository.deleteById(answerRequest.userId());
            } else {
                throw new AlreadyException("you probably spelled it wrong must be accepted or not accepted");
            }
        }
        return SimpleResponse.builder().status(HttpStatus.OK).
                message(String.format("Employee with fullName : %s job title: %s " +
                                "successfully saved",
                        user.getFirstName().concat(user.getLastName()), user.getRole())).build();
    }
    @Override
    public UserPaginationResponse getUserPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("name"));
        Page<User> userPage = userRepository.findAll(pageable);
        UserPaginationResponse userPaginationResponse = new UserPaginationResponse();
        userPaginationResponse.setUserResponses(convert(userPage.getContent()));
        userPaginationResponse.setCurrentPage(pageable.getPageNumber());
        userPaginationResponse.setCurrentPageSize(userPage.getTotalPages());
        return userPaginationResponse;
    }

    @PostConstruct
    public void initSaveAdmin() {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            user.setFirstName("Syimyk");
            user.setLastName("Zhumabek uulu");
            user.setExperience((byte)4);
            user.setDateOfBirth(LocalDate.of(2000, 9, 25));
            user.setPhoneNumber("0202");
            if (!userRepository.existsByEmail(user.getEmail())) {
                userRepository.save(user);
            }
   }
    private UserResponse convert(User user) {
        return UserResponse.builder()
                        .id(user.getId())
                .dateOfBirth(user.getDateOfBirth())
                .experience(user.getExperience())
                .fullName(user.getFirstName()+" "+user.getLastName())
                .phoneNumber(user.getPhoneNumber()).build();
    }

    private List<UserResponse> convert(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(convert(user));
        }
        return userResponses;
    }
}

