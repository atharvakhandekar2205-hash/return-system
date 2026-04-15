package com.example.returnsystem.controller;

import com.example.returnsystem.model.Return;
import com.example.returnsystem.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    // ✅ Home API (IMPORTANT FIX)
    @GetMapping("/")
    public String home() {
        return "Return System API Running 🚀";
    }

    // Add return (POST)
    @PostMapping("/add")
    public Return add(@RequestBody Return r) {
        return returnService.processReturn(r);
    }

    // Get all returns
    @GetMapping("/all")
    public List<Return> getAll() {
        return returnService.getAll();
    }

    // Stats API
    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return returnService.getStats();
    }
}