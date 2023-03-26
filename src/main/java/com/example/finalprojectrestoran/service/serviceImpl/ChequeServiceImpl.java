package com.example.finalprojectrestoran.service.serviceImpl;
import com.example.finalprojectrestoran.dto.requests.ChequeOfRestaurantAmountDayRequest;
import com.example.finalprojectrestoran.dto.requests.ChequeOneDayTotalAmountRequest;
import com.example.finalprojectrestoran.dto.requests.ChequeRequest;
import com.example.finalprojectrestoran.dto.requests.ChequeUpdateRequest;
import com.example.finalprojectrestoran.dto.responses.*;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.ChequePaginationResponse;
import com.example.finalprojectrestoran.entity.Cheque;
import com.example.finalprojectrestoran.entity.MenuItem;
import com.example.finalprojectrestoran.entity.Restaurant;
import com.example.finalprojectrestoran.entity.User;
import com.example.finalprojectrestoran.entity.enums.Role;
import com.example.finalprojectrestoran.exception.AlreadyException;
import com.example.finalprojectrestoran.exception.BadRequestException;
import com.example.finalprojectrestoran.exception.NotFoundException;
import com.example.finalprojectrestoran.repository.*;
import com.example.finalprojectrestoran.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final ChequeRepository chequeRepository;
    private final RestaurantRepository restaurantRepository;
    private final StopListRepository stopListRepository;

    @Override
    public List<ChequeResponse> findAllCheque() {
        List<Cheque> chequeList = chequeRepository.findAll();
        List<ChequeResponse> chequeResponseList = new ArrayList<>();
        for (Cheque cheque : chequeList) {
            int averagePrice = 0;
            for (MenuItem menuItem : cheque.getMenuItems()) {
                averagePrice += menuItem.getPrice().intValue();
            }
            int service = averagePrice * cheque.getUser().getRestaurant().getService() / 100;
            int grandTotal = service + averagePrice;
            ChequeResponse build = ChequeResponse.builder().
                    id(cheque.getId())
                    .service(cheque.getUser().getRestaurant().getService())
                    .priceAverage(BigDecimal.valueOf(averagePrice))
                    .WaiterFullName((cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName()))
                    .grandTotal(BigDecimal.valueOf((grandTotal)))
                    .menuItems(convert(cheque.getMenuItems())).build();
            chequeResponseList.add(build);
        }
        return chequeResponseList;
    }

    @Override
    public ChequeResponse saveCheque(ChequeRequest chequeRequest) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        int averagePrice = 0;
        for (String name : chequeRequest.menuItemName()) {
            MenuItem menuItem = menuItemRepository.findByName(name).orElseThrow(() -> new NotFoundException("Not found"));
            if (stopListRepository.count(LocalDate.now(), menuItem.getName()) > 0) {
                throw new BadRequestException("has already");
            }
            menuItem.addCheques(cheque);
            averagePrice += menuItem.getPrice().intValue();
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(chequeRequest.waiterId()).orElseThrow(
                () -> new NotFoundException("User with id : " + chequeRequest.waiterId() + "is not found!"));
        if (user.getRole().equals(Role.WALTER)) {
            cheque.setCreatedAt(LocalDate.now());
            cheque.setPriceAverage(BigDecimal.valueOf(averagePrice));
            user.addCheques(cheque);
            cheque.setUser(user);
            cheque.setMenuItems(menuItems);
            chequeRepository.save(cheque);
        } else {
            throw new AlreadyException("UserRole with role :" + user.getRole() + "not found!");
        }
        int service = averagePrice * user.getRestaurant().getService() / 100;
        int grandTotal = service + averagePrice;
        return ChequeResponse.builder()
                .id(cheque.getId())
                .service(user.getRestaurant().getService())
                .priceAverage(BigDecimal.valueOf(averagePrice))
                .WaiterFullName((user.getFirstName() + " " + user.getLastName()))
                .grandTotal(BigDecimal.valueOf((grandTotal)))
                .menuItems(convert(menuItems)).build();
    }

    @Override
    public ChequeResponse findById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        int averagePrice = 0;
        for (MenuItem menuItem : cheque.getMenuItems()) {
            averagePrice += menuItem.getPrice().intValue();
        }
        int service = averagePrice * cheque.getUser().getRestaurant().getService() / 100;
        int grandTotal = service + averagePrice;
        return ChequeResponse.builder()
                .id(cheque.getId())
                .service(cheque.getUser().getRestaurant().getService())
                .priceAverage(BigDecimal.valueOf(averagePrice))
                .WaiterFullName((cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName()))
                .grandTotal(BigDecimal.valueOf((grandTotal)))
                .menuItems(convert(cheque.getMenuItems())).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("Cheque with id : " + id + "is not found"));
        cheque.getMenuItems().forEach(menuItem -> menuItem.setCheques(null));
        chequeRepository.delete(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Cheque with id : %s is deleted!", id)).build();
    }

    @Override
    public ChequeOneDayTotalAmountResponse findAllChequesOneDayTotalAmount(ChequeOneDayTotalAmountRequest chequeOneDayTotalAmountRequest) {
        User user = userRepository.findById(chequeOneDayTotalAmountRequest.walterId()).orElseThrow(
                () -> new NotFoundException("User with id : " + chequeOneDayTotalAmountRequest.walterId() + "is not found!"));
        int chequeCount = 0;
        int totalAmount = 0;
        if (user.getRole().equals(Role.WALTER)) {
            for (Cheque che : user.getCheques()) {
                if (che.getCreatedAt().isEqual(chequeOneDayTotalAmountRequest.date())) {
                    int service = che.getPriceAverage().intValue() * user.getRestaurant().getService() / 100;
                    totalAmount += service + che.getPriceAverage().intValue();
                    ++chequeCount;
                }
            }
        }else {
            throw new AlreadyException();
        }
        return ChequeOneDayTotalAmountResponse.builder()
                .totalAmount(BigDecimal.valueOf(totalAmount))
                .numberOfCheques(chequeCount)
                .walterFullName(user.getFirstName() + " " + user.getLastName())
                .build();

    }

    @Override
    public ChequeOfRestaurantAmountDayResponse countRestGrantTotalForDay(ChequeOfRestaurantAmountDayRequest chequeOfREstaurantAmountDayRequest) {
        Restaurant restaurant = restaurantRepository.findRestaurantByName(chequeOfREstaurantAmountDayRequest.restaurantName()).orElseThrow(
                () -> new NotFoundException("Restaurant with name: " + chequeOfREstaurantAmountDayRequest.restaurantName() + "is not found!"));
        int numberOfWaiters = 0;
        int numberOfCheque = 0;
        int totalAmount = 0;
        for (User userWaiter : restaurant.getUsers()) {
            if (userWaiter.getRole().equals(Role.WALTER)) {
                for (Cheque waiterCh : userWaiter.getCheques()) {
                    if (waiterCh.getCreatedAt().isEqual(chequeOfREstaurantAmountDayRequest.date())) {
                        int restaurantService = waiterCh.getPriceAverage().intValue() * restaurant.getService() / 100;
                        totalAmount += restaurantService + waiterCh.getPriceAverage().intValue();
                        numberOfCheque++;
                    }

                }
                numberOfWaiters++;
            }

        }
        ChequeOfRestaurantAmountDayResponse chequeOfRestaurantAmountDayResponse = new ChequeOfRestaurantAmountDayResponse();
        chequeOfRestaurantAmountDayResponse.setPriceAverage(totalAmount/numberOfCheque);
        chequeOfRestaurantAmountDayResponse.setNumberOfCheque(numberOfCheque);
        chequeOfRestaurantAmountDayResponse.setNumberOfWaiters(numberOfWaiters);
        return chequeOfRestaurantAmountDayResponse;
    }

    @Override
    public SimpleResponse update(ChequeUpdateRequest chequeUpdateRequest) {
        Cheque cheque = chequeRepository.findById(chequeUpdateRequest.chequeId()).orElseThrow(() -> new NotFoundException("Cheque with id:" + chequeUpdateRequest.chequeId() + "not found!"));
        User user = userRepository.findById(chequeUpdateRequest.waiterId()).orElseThrow(
                () -> new NotFoundException("User with id : " + chequeUpdateRequest.waiterId() + "is not found!"));
        if (user.getRole().equals(Role.WALTER)) {
            List<MenuItem> menuItems = cheque.getMenuItems();
            for (String oldMenuITemName : chequeUpdateRequest.oldMenuItemName()) {
                MenuItem menuItem = menuItemRepository.findByName(oldMenuITemName).orElseThrow(() -> new NotFoundException("MenuItem with name : " + oldMenuITemName + "is not found!"));
                menuItems.remove(menuItem);
            }
//            oldMenuItems.add(menuItem);
//        }
//        List<MenuItem> menuItems = new ArrayList<>();
//        for (MenuItem menuItem:oldMenuItems) {
//            for (MenuItem m: cheque.getMenuItems()) {
//                if (!m.getName().equalsIgnoreCase(menuItem.getName())){
//                    menuItems.add(m);
//                }
//
//            }
//            System.out.println(menuItems);
//        }
            List<MenuItem> menuItemList = new ArrayList<>();
            for (String newMenuItem : chequeUpdateRequest.menuItemsItemName()) {
                MenuItem menuItem = menuItemRepository.findByName(newMenuItem).orElseThrow(() -> new NotFoundException("MenuItem with name : " + newMenuItem + "is not found!"));
                menuItemList.add(menuItem);
                menuItem.addCheques(cheque);
            }
            menuItemList.addAll(menuItems);
            System.out.println(menuItemList);
            cheque.setUser(user);
            user.addCheques(cheque);
            cheque.setMenuItems(menuItemList);
            chequeRepository.save(cheque);
        }else {

        }
       return SimpleResponse.builder().status(HttpStatus.OK)
                .message("Cheque successfully update").build();
    }

    @Override
    public ChequePaginationResponse getPagination(int size, int page) {
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("grandTotal"));
        Page<Cheque> chequePage = chequeRepository.findAll(pageable);
        ChequePaginationResponse chequePaginationResponse = new ChequePaginationResponse();
        chequePaginationResponse.setMenuItemResponses(convert2(chequePage.getContent()));
        chequePaginationResponse.setCurrentPage(pageable.getPageNumber());
        chequePaginationResponse.setCurrentPageSize(chequePage.getTotalPages());
        return chequePaginationResponse;
    }


    private MenuItemResponse convert(MenuItem menuItem) {
        return MenuItemResponse.builder().
                id(menuItem.getId()).
                price(menuItem.getPrice())
                .image(menuItem.getImage())
                .description(menuItem.getDescription())
                .isVegetarian(menuItem.getIsVegetarian())
                .name(menuItem.getName()).build();
    }

    private List<MenuItemResponse> convert(List<MenuItem> menuItems) {
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(convert(menuItem));
        }
        return menuItemResponses;
    }
    private ChequeResponse convert1(Cheque cheque){
        int averagePrice = 0;
        for (MenuItem m:cheque.getMenuItems()) {
            averagePrice += m.getPrice().intValue();
        }
        int service = averagePrice *cheque.getUser().getRestaurant().getService() / 100;
        int grandTotal = service + averagePrice;
        return ChequeResponse.builder()
                .id(cheque.getId())
                .service(cheque.getUser().getRestaurant().getService())
                .WaiterFullName(cheque.getUser().getFirstName()+" " + cheque.getUser().getLastName())
                .priceAverage(BigDecimal.valueOf(averagePrice))
                .grandTotal(BigDecimal.valueOf(grandTotal)).build();
    }
    private List<ChequeResponse> convert2(List<Cheque> chequeList){
        List<ChequeResponse> chequeResponseList = new ArrayList<>();
        for (Cheque c:chequeList) {
            chequeResponseList.add(convert1(c));
        }
        return chequeResponseList;
    }
}
