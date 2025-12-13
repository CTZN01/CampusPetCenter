package com.ashen.petcenter;

import com.ashen.petcenter.entity.Pet;
import com.ashen.petcenter.mapper.PetMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@Tag(name = "Pet Management", description = "APIs for managing pets")
public class PetController {

    @Autowired
    private PetMapper petMapper;

    @GetMapping
    public List<Pet> list() {
        return petMapper.selectList(new QueryWrapper<>());
    }

    @GetMapping("/{id}")
    public Pet get(@PathVariable Long id) {
        return petMapper.selectById(id);
    }

    @PostMapping
    public Pet create(@RequestBody Pet pet) {
        petMapper.insert(pet);
        return pet;
    }

    @PutMapping("/{id}")
    public Pet update(@PathVariable Long id, @RequestBody Pet pet) {
        pet.setId(id);
        petMapper.updateById(pet);
        return pet;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        petMapper.deleteById(id);
    }
}

