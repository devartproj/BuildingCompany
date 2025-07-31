        for (BuildingOperationInfo info : highPriorityOps) {
            String name = info.isClassLevel() ? info.getClassName() : info.getMethodName();
            LOGGER.info("High priority operation: {} (Priority: {})", 
                    name, info.getPriority());
        }
