expert.newRule("Bicycle")
    .ifEquals("vehicleType", "cycle")
    .andEquals("num_wheels", 2)
    .andEquals("motor", "no")
    .thenEquals("vehicle", "Bicycle")
    .build();

expert.newRule("Tricycle")
    .ifEquals("vehicleType", "cycle")
    .andEquals("num_wheels", 3)
    .andEquals("motor", "no")
    .thenEquals("vehicle", "Tricycle")
    .build();

expert.newRule("Motorcycle")
    .ifEquals("vehicleType", "cycle")
    .andEquals("num_wheels", 2)
    .andEquals("motor", "yes")
    .thenEquals("vehicle", "Motorcycle")
    .build();

expert.newRule("SportsCar")
    .ifEquals("vehicleType", "automobile")
    .andEquals("size", "medium")
    .andEquals("num_doors", 2)
    .thenEquals("vehicle", "Sports_Car")
    .build();

expert.newRule("Sedan")
    .ifEquals("vehicleType", "automobile")
    .andEquals("size", "medium")
    .andEquals("num_doors", 4)
    .thenEquals("vehicle", "Sedan")
    .build();

expert.newRule("MiniVan")
    .ifEquals("vehicleType", "automobile")
    .andEquals("size", "medium")
    .andEquals("num_doors", 3)
    .thenEquals("vehicle", "MiniVan")
    .build();

expert.newRule("SUV")
    .ifEquals("vehicleType", "automobile")
    .andEquals("size", "large")
    .andEquals("num_doors", 4)
    .thenEquals("vehicle", "SUV")
    .build();

expert.newRule("Cycle")
    .ifLess("num_wheels", 4)
    .thenEquals("vehicleType", "cycle")
    .build();

expert.newRule("Automobile")
    .ifEquals("num_wheels", 4)
    .andEquals("motor", "yes")
    .thenEquals("vehicleType", "automobile")
    .build();

var System = Java.type('java.lang.System');

expert.clearFacts();
expert.addFact("num_wheels", 4);
expert.addFact("motor", "yes");
expert.addFact("num_doors", 3);
expert.addFact("size", "medium");

System.out.println("Before inference");
System.out.println(expert.getKnowledgeBase());

System.out.println('===============infer=====================');
expert.infer();
System.out.println('===============infer=====================');

System.out.println("After inference")
System.out.println(expert.getKnowledgeBase());