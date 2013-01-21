class Acceptance
	def method1
		puts "calling method 1"
		"ok"
	end
	
	def method2 int_param
		int_param * int_param
	end 
	
	def method3 string_param
		string_param + " appended by ruby"
	end
	
	def method4 array
		array.size
	end
end