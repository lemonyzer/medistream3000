package com.shortcutz.medistream3000.htmx;

//import com.github.javafaker.Faker;
import com.shortcutz.medistream3000.data.Appointment
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

//    private final Faker faker;
//
//    public ContactsController(Faker faker) {
//        this.faker = faker;
//    }

    @GetMapping(value = [""], produces = [MediaType.TEXT_HTML_VALUE])
    fun  list( model: Model) : String{
        model.addAttribute("contacts", createContacts(3));
        return "03/index";
    }

    @GetMapping("/load")
    fun getRows(model :Model) : String{
        model.addAttribute("contacts", createContacts(3));
        return "03/contact-row :: contact-row";
    }

    fun createContacts(count : Int) : ArrayList<Contact> {
        val contactList = arrayListOf<Contact>()
        for (i in 1..count) {
            contactList.add(
                Contact(
                    "faker.name().firstName()" + " " + "faker.name().lastName()",
                    "faker.internet().emailAddress()",
                    UUID.randomUUID().toString()
                )
            )
            //staticLogger.info("---> saving Contact $i")
            //appointmentRepo.save(Appointment(0,UUID.randomUUID(), medistreamId = (i%2).toLong()))//PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", rooms.get(i)))
//            patientRepo.save(PatientEntity(UUID.randomUUID(),i.toLong(),"$i","Nach${i + i}", RoomEntity(UUID.randomUUID(),"room-$i",i,2)))
        }
        return contactList
    }
//        return IntStream.rangeClosed(1,count)
//                .mapToObj(Contact(
//                        "faker.name().firstName()" + " " + "faker.name().lastName()",
//                        "faker.internet().emailAddress()",
//                        UUID.randomUUID().toString()
//                )).toList();
//    }

}
