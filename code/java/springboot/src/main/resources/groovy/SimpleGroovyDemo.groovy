@RestController
class ThisWillActuallyRun{

	@GetMapping("/home")
	String home(){
		"Hello Groovy"
	}
}