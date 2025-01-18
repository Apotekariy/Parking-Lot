package parking

const val message = "Sorry, a parking lot has not been created."

fun main() {
    var parkingLot = mutableListOf<ParkingSpace>()

    while (true) {
        val userInput = readln()
        val userInputList = userInput.split(" ")
        val command = userInputList[0]
        if (command.isEmpty()) {
            println("Empty String")
        }
        else when (command) {
            "exit" -> break
            "create" -> {
                val capacity = userInputList[1].toInt()
                parkingLot = createParkingLot(capacity)
                println("Created a parking lot with $capacity spots.")
            }
            "park" -> park(parkingLot, userInputList)
            "leave" -> leave(parkingLot, userInputList)
            "status" -> status(parkingLot)
            "reg_by_color" -> regByColor(parkingLot, userInputList)
            "spot_by_color" -> spotByColor(parkingLot, userInputList)
            "spot_by_reg" -> spotByReg(parkingLot, userInputList)
            }
        }
}

fun createParkingLot(capacity: Int): MutableList<ParkingSpace> {
    return MutableList(capacity) {
        ParkingSpace(empty = true, regNumber = "", spotNumber = it + 1, color = "")
    }
}

fun park (parkingLot: MutableList<ParkingSpace>, userInputList: List<String>): MutableList<ParkingSpace>  {
    when {
        parkingLot.size == 0 -> println(message)
        parkingLot.all { !it.empty } -> println("Sorry, the parking lot is full.")
        else -> {
            val nextAvailable = parkingLot.indexOfFirst { it.empty }
            parkingLot[nextAvailable].empty = false
            parkingLot[nextAvailable].regNumber = userInputList[1]
            parkingLot[nextAvailable].color = userInputList[2]
            println("${parkingLot[nextAvailable].color} car parked in spot ${nextAvailable + 1}.")
        }
    }
    return parkingLot
}

fun status(parkingLot: MutableList<ParkingSpace>) {
    when {
        parkingLot.size == 0 -> println(message)
        parkingLot.any { !it.empty } -> {
            parkingLot.forEach {space ->
                if (!space.empty) {
                    println("${space.spotNumber} ${space.regNumber} ${space.color}")
                }
            }
        }
        else -> println("Parking lot is empty.")
    }
}

fun leave(parkingLot: MutableList<ParkingSpace>, userInputList: List<String>): MutableList<ParkingSpace> {
    val inputIndex = userInputList[1].toInt() - 1
    when {
        parkingLot.size == 0 -> println(message)
        inputIndex in (0..parkingLot.lastIndex) -> {
            if (inputIndex in 0 until parkingLot.size) { // Check if inputIndex is within bounds
                if (!(parkingLot[inputIndex].empty)) {
                    parkingLot[inputIndex].empty = true
                    parkingLot[inputIndex].regNumber = ""
                    parkingLot[inputIndex].color = ""
                    println("Spot ${inputIndex + 1} is free.")
                } else println("There is no car in spot ${inputIndex + 1}.")
            }
        }
        else -> println("Invalid parking spot number.")
    }
    return parkingLot
}

fun regByColor(parkingLot: MutableList<ParkingSpace>, userInputList: List<String>) {
    val color = userInputList[1].uppercase()
    val filtered = parkingLot.filter { it.color.uppercase() == color }
    when {
        parkingLot.size == 0 -> println(message)
        filtered.isEmpty() -> println("No cars with color $color were found.")
        else -> println(filtered.joinToString(", ") { it.regNumber })
    }
}

fun spotByColor(parkingLot: MutableList<ParkingSpace>, userInputList: List<String>) {
    val color = userInputList[1].uppercase()
    val filtered = parkingLot.filter { it.color.uppercase() == color }
    when {
        parkingLot.size == 0 -> println(message)
        filtered.isEmpty() -> println("No cars with color $color were found.")
        else -> println(filtered.joinToString(", ") { it.spotNumber.toString() })
    }
}

fun spotByReg (parkingLot: MutableList<ParkingSpace>, userInputList: List<String>) {
    val reg = userInputList[1].uppercase()
    val filtered = parkingLot.filter { it.regNumber.uppercase() == reg }
    when {
        parkingLot.size == 0 -> println(message)
        filtered.isEmpty() -> println("No cars with registration number $reg were found.")
        else -> println(filtered.joinToString(", ") { it.spotNumber.toString() })
    }
}