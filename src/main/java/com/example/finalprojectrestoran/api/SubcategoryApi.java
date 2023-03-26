package com.example.finalprojectrestoran.api;

import com.example.finalprojectrestoran.dto.requests.SubcategoryRequest;
import com.example.finalprojectrestoran.dto.responses.SimpleResponse;
import com.example.finalprojectrestoran.dto.responses.SubcategoryResponse;
import com.example.finalprojectrestoran.dto.responses.paginationResponse.SupCategoryPaginationResponse;
import com.example.finalprojectrestoran.entity.Subcategory;
import com.example.finalprojectrestoran.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subcategory")
public class SubcategoryApi {
    private final SubCategoryService subCategoryService;
    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Subcategory> findAll(){
        return subCategoryService.findAll();
    }
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody SubcategoryRequest subcategoryRequest){
        return subCategoryService.save(subcategoryRequest);
    }
    @GetMapping("/sub/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SubcategoryResponse findById(@PathVariable Long id){
        return subCategoryService.findById(id);
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long id){
        return subCategoryService.delete(id);
    }
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public  SimpleResponse update(@PathVariable Long id , @RequestBody SubcategoryRequest subcategory){
        return subCategoryService.update(id,subcategory);
    }
    @GetMapping("/{categoryId}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Subcategory> findAllCategoryId(@PathVariable Long categoryId){
        return subCategoryService.findAllCategoryById(categoryId);
    }
    @GetMapping("/findAll")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public Map<String, List<Subcategory>> findAllGroupByCategory(){
        return subCategoryService.findAllGroupByCategory();
    }
    @GetMapping("pagination")
    public SupCategoryPaginationResponse getPagination(@RequestParam int size,@RequestParam int page){
        return subCategoryService.getSubcategoryPagination(size, page);
    }
}
