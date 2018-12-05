#!/usr/bin/env ruby

def reaction?(unit1, unit2)
  unit1.downcase == unit2.downcase && unit1 != unit2
end

def polymer_reaction(polymer)
  index = 0

  polymer_units = polymer.chars

  while true
    unit1 = polymer_units[index]
    unit2 = polymer_units[index + 1]

    break if unit2.nil?

    if reaction?(unit1, unit2)
      polymer_units.delete_at(index + 1)
      polymer_units.delete_at(index)
      index -= 1 if index > 0
    else
      index += 1
    end
  end

  polymer_units.join
end

def unit_types(polymer)
  polymer.downcase.chars.uniq
end

polymer = File.readlines('input.txt').first.strip

polymer_result = polymer_reaction(polymer)

puts "Solution 1: #{polymer_result.size}"

min_length = Float::INFINITY

unit_types(polymer).each do |unit|
  result_polymer = polymer.chars.reject { |c| c.downcase == unit }.join
  result_polymer = polymer_reaction(result_polymer)

  result_polymer.size < min_length && min_length = result_polymer.size
end

puts "Solution 2: #{min_length}"
