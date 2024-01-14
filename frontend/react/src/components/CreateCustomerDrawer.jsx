import { Button,
        useDisclosure,
        Drawer,
        DrawerOverlay,
        DrawerBody,
        DrawerContent,
        DrawerCloseButton,
        DrawerHeader,
        Input,
        DrawerFooter
} from "@chakra-ui/react"

import CreateCustomerForm from "./CreateCustomerForm";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const DrawerForm = ({ fetchCustomers }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button leftIcon={<AddIcon></AddIcon>} colorScheme="teal" onClick={onOpen}>
            Create customer
        </Button>

        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
          <DrawerOverlay />
          <DrawerContent>
            <DrawerCloseButton />
            <DrawerHeader>Create new customer</DrawerHeader>
  
            <DrawerBody>
              <CreateCustomerForm fetchCustomers={fetchCustomers}></CreateCustomerForm>
            </DrawerBody>
  
            <DrawerFooter>
            <Button leftIcon={<CloseIcon></CloseIcon>} colorScheme="teal" onClick={onClose}>
              Close
            </Button>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
    </>
    
}

export default DrawerForm;
