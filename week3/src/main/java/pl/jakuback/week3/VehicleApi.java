package pl.jakuback.week3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/vehicles")
public class VehicleApi {

    private List<Vehicle> vehicleList;

    public VehicleApi() {
        this.vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L,"Audi","A5",Color.BLACK));
        vehicleList.add(new Vehicle(2L,"BMW","M2",Color.WHITE));
        vehicleList.add(new Vehicle(3L,"Porsche","Panamera",Color.WHITE));
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles(){
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id){
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
        if (first.isPresent()){
            return new ResponseEntity<>(first.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Stream<Vehicle>> getVehiclesByColor(@PathVariable Color color){
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getColor() == color).findFirst();
        if (first.isPresent()){
            return new ResponseEntity<>(vehicleList.stream().filter(vehicle -> vehicle.getColor() == color),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle){
        boolean add = vehicleList.add(vehicle);
        if (add){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity modVehicle(@RequestBody Vehicle newVehicle){
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == newVehicle.getId()).findFirst();
        if (first.isPresent()){
            vehicleList.remove(first.get());
            vehicleList.add(newVehicle);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicle> removeVehicleById(@PathVariable long id){
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
        if (first.isPresent()){
            vehicleList.remove(first.get());
            return new ResponseEntity<>(first.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
